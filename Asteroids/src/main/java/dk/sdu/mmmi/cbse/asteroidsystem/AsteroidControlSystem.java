package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double x = Math.cos(Math.toRadians(asteroid.getRotation()));
            double y = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() - x/2);
            asteroid.setY(asteroid.getY() - y/2);

            if(asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() - gameData.getDisplayWidth());
            }
            if(asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() + gameData.getDisplayWidth());
            }
            if(asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() - gameData.getDisplayHeight());
            }
            if(asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() + gameData.getDisplayHeight());
            }

        }
    }
}
