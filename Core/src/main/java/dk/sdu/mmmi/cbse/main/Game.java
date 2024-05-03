package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.util.stream.Collectors.toList;

public class Game {
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private final Text scoreText = new Text("Destroyed asteroids: 0");
    private long lastPointUpdateTime;



    /*
    public static void main(String[] args) {
        launch(Main.class);
    }
    */
    public Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        scoreText.setX(50);
        scoreText.setY(50);
        scoreText.setFill(Color.BLACK);
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : gamePluginServices) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        //reset score when app starts, as spring boot doesnt close when the game does
        String url = "http://localhost:8081/reset";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }

    private void update() {

        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessingServices) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessingServices) {
            postEntityProcessorService.process(gameData, world);
            if (postEntityProcessorService instanceof CollisionDetection) {
                ((CollisionDetection) postEntityProcessorService).setPolygons(polygons);
            }
        }
        //remove entities(Bullets) if they go outside the game screen, as to not spawn too many entities
        for (Entity entity : world.getEntities()) {
            if (entity.getX() < 0
                    || entity.getX() > gameData.getDisplayWidth()
                    || entity.getY() < 0
                    || entity.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(entity);
                Polygon polygon = polygons.get(entity);
                gameWindow.getChildren().remove(polygon);
                polygons.remove(entity);
            }
        }
        //removes polygons from entity on death since removeEntity() doesnt do that
        polygons.entrySet().removeIf(entry -> {
            if (!world.getEntities().contains(entry.getKey())) {
                gameWindow.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });
        updateScore();


    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

    }

    private void updateScore() {
        if (world.getEntitiesType(EntityType.PLAYER).isEmpty()) {
            return;
        } else {
            //Limit the amount of requests to the server to once per sec
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPointUpdateTime >= 1000) {
                String url = "http://localhost:8081/getScore";
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                int score = 0;

                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String response2 = response.body();
                    score = Integer.parseInt(response2);
                    lastPointUpdateTime = currentTime;
                    scoreText.setText("Score: " + score);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
