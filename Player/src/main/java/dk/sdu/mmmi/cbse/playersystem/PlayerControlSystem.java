package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {
    private int shotDelayMilisec = 300;

    @Override
    public void process(GameData gameData, World world) {
        double movementSpeedModifier = 0.3;
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 1);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 1);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + (changeX * movementSpeedModifier));
                player.setY(player.getY() + (changeY * movementSpeedModifier));
            }
            //delay shot by shotDelayMilisec as to not fully machine gun everything
            if (gameData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - gameData.getLastShotTimer() > shotDelayMilisec) {
                getBulletSPIs().stream().findFirst().ifPresent(spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );
                gameData.setLastShotTimer(System.currentTimeMillis());

                /*
                To show how the different streams can be used
                getBulletSPIs().stream().filter(spi -> spi.getClass().getName().contains("Bullet")).findFirst().ifPresent(spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );

                getBulletSPIs().stream().findAny().ifPresent(spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );
                */

            }

            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth() - 1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight() - 1);
            }


        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
