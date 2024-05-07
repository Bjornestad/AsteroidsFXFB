package dk.sdu.mmmi.cbse.splitAsteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidSplitMovement implements AsteroidSplitterSPI, IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            asteroidBounceOnWalls(asteroid, gameData);
        }
    }


    @Override
    public List<Entity> createSplitAsteroid(Entity hitAsteroid, GameData gameData){
        //Splits the asteroid like a pizza
        List<Entity> splitAsteroids = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Asteroid asteroid = new Asteroid();
            asteroid.setType(EntityType.ASTEROID_SPLIT);
            asteroid.setPolygonCoordinates(0, 0, 10, 0, 5, 8.66);
            asteroid.setX(hitAsteroid.getX());
            asteroid.setY(hitAsteroid.getY());
            asteroid.setRadius(5);
            asteroid.setRotation(hitAsteroid.getRotation() + i * 60);
            asteroid.setSplitAble(false);
            splitAsteroids.add(asteroid);

        }
        return splitAsteroids;
    }


    private void asteroidBounceOnWalls(Entity asteroid, GameData gameData) {
        double x = asteroid.getX();
        double y = asteroid.getY();
        double dx = Math.cos(Math.toRadians(asteroid.getRotation()));
        double dy = Math.sin(Math.toRadians(asteroid.getRotation()));
        double width = gameData.getDisplayWidth();
        double height = gameData.getDisplayHeight();
        double radius = asteroid.getRadius();
        x += (dx * 0.01);
        y += (dy * 0.01);


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
