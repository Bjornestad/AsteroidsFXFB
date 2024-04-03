package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for(Entity enemy : world.getEntities(Enemy.class)){
            Random rn = new Random();
            int dn = rn.nextInt((10-2)+10);
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.cos(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            if(dn >= 5){
                enemy.setRotation(enemy.getRotation() + 5);
            }
            if (dn < 5){
                enemy.setRotation(enemy.getRotation() - 5);
            }
            if (dn >= 8){
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity((spi.createBullet(enemy, gameData)));}
                );
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs(){
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
