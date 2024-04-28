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

        int enemyCount = world.getEntities(Enemy.class).size();

        if(Math.random()*100 > 99 && enemyCount < 3){
            Enemy enemy = new EnemyCreator().createEnemy(gameData, world);
            world.addEntity(enemy);
        }

        for(Entity enemy : world.getEntities(Enemy.class)){

            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.cos(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);
            double width = gameData.getDisplayWidth();
            double height = gameData.getDisplayHeight();

            if(enemy.getX() < 0){
                enemy.setRotation(enemy.getRotation() + 5);
            }
            if(enemy.getX() > width){
                enemy.setRotation(enemy.getRotation() - 5);
            }
            if(enemy.getY() < 0){
                enemy.setRotation(enemy.getRotation() + 5);
            }
            if(enemy.getY() > height){
                enemy.setRotation(enemy.getRotation() - 5);
            }


        if(Math.random()*100 > 99){
            getBulletSPIs().forEach(BulletSPI ->{
                Entity bullet = BulletSPI.createBullet(enemy, gameData);

                world.addEntity(bullet);
            });
            }
        }

    }

    private Collection<? extends BulletSPI> getBulletSPIs(){
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
