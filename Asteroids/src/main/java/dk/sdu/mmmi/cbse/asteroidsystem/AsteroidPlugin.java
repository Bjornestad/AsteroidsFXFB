package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService, AsteroidSPI {
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData, world);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
    @Override
    public Entity createAsteroid(GameData gameData, World world){
        Entity asteroid = new Asteroid();
        asteroid.setType(EntityType.ASTEROID);
        Random rn = new Random();

        asteroid.setPolygonCoordinates(0,0,10,0,15,8.66,10,17.32,0,17.32,-5,8.66);
        asteroid.setX(gameData.getDisplayHeight()*Math.random());
        asteroid.setY(gameData.getDisplayWidth()*Math.random());
        asteroid.setRadius(10);
        asteroid.setRotation(rn.nextDouble()*360);
        asteroid.setSplitAble(true);
        return asteroid;
    }
}
