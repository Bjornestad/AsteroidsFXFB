package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidCreator implements AsteroidSPI{
    @Override
    public Asteroid createAsteroid(GameData gameData, World world){
        Asteroid asteroid = new Asteroid();
        asteroid.setType(EntityType.ASTEROID);
        Random rn = new Random();

        asteroid.setPolygonCoordinates(0,0,10,0,15,8.66,10,17.32,0,17.32,-5,8.66);
        asteroid.setX(gameData.getDisplayHeight()*Math.random());
        asteroid.setY(gameData.getDisplayWidth()*Math.random());
        asteroid.setRadius(10);
        asteroid.setRotation(rn.nextDouble()*360);
        return asteroid;
    }

}
