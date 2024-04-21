package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

public class AsteroidCreator {
    public Asteroid createAsteroid(GameData gameData, World world){
        Asteroid asteroid = new Asteroid();
        asteroid.setType(EntityType.ASTEROID);
        Random rn = new Random();
        double radius = rn.nextInt(10);

        asteroid.setPolygonCoordinates(0, radius, radius, radius, radius, 0, 0, -radius, -radius, -radius, -radius, 0, 0, radius);
        asteroid.setX(gameData.getDisplayHeight()*Math.random());
        asteroid.setY(gameData.getDisplayWidth()*Math.random());
        asteroid.setRadius((float) radius);
        asteroid.setRotation(rn.nextDouble()*360);
        return asteroid;
    }
}
