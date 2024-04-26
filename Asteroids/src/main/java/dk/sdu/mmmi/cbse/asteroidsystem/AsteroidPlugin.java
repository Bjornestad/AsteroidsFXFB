package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = new AsteroidCreator().createAsteroid(gameData, world);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }


    }
}