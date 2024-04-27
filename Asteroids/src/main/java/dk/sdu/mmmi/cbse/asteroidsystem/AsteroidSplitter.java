package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidSplitter implements AsteroidSplitterSPI {

    @Override
    public List<Asteroid> createSplitAsteroid(Entity entity, World world) {
        //Splits the asteroid like a pizza
        List<Asteroid> splitAsteroids = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            Asteroid asteroid = new Asteroid();
            asteroid.setType(EntityType.ASTEROID);
            asteroid.setPolygonCoordinates(0,0,10,0,5,8.66);
            asteroid.setX(entity.getX());
            asteroid.setY(entity.getY());
            asteroid.setRadius(5);
            asteroid.setRotation(entity.getRotation() + i * 60);
            asteroid.setSplitAble(false);
            splitAsteroids.add(asteroid);
        }
        return splitAsteroids;
    }
}
