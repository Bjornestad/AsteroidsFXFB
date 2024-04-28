package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSpillter;

import java.util.Random;

public class AsteroidMovement implements IEntityProcessingService {

    int maxAsteroidSpawns = 4;
    int maxSmallAsteroids = 24;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            asteroidBounceOnWalls(asteroid, gameData);
        }

        if (world.getEntities(Asteroid.class).size() < maxAsteroidSpawns && world.getEntities(AsteroidSplitter.class).size() < maxSmallAsteroids){
            Entity asteroid = new AsteroidCreator().createAsteroid(gameData, world);
            world.addEntity(asteroid);
            System.out.println("Asteroid spawned");
        }
    }


    private void asteroidBounceOnWalls(Entity asteroid, GameData gameData) {
        double x = asteroid.getX();
        double y = asteroid.getY();
        double dx = Math.cos(Math.toRadians(asteroid.getRotation()));
        double dy = Math.sin(Math.toRadians(asteroid.getRotation()));
        double width = gameData.getDisplayWidth();
        double height = gameData.getDisplayHeight();
        double radius = asteroid.getRadius();
        x += dx;
        y += dy;


        if (x - radius < 0) {
            x = radius;
            asteroid.setRotation(180 - asteroid.getRotation());
        } else if (x + radius > width) {
            x = width - radius;
            asteroid.setRotation(180 - asteroid.getRotation());
        }

        if (y - radius < 0) {
            y = radius;
            asteroid.setRotation(360 - asteroid.getRotation());
        } else if (y + radius > height) {
            y = height - radius;
            asteroid.setRotation(360 - asteroid.getRotation());
        }

        asteroid.setX(x);
        asteroid.setY(y);
    }
}
