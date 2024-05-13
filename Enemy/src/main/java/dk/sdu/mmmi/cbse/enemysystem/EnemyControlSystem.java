package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private int shotDelayMilisec = 800;

    @Override
    public void process(GameData gameData, World world) {
        if(world.getEntitiesType(EntityType.ENEMY).size() < 3){
            Entity enemy = new EnemyPlugin().createEnemy(gameData, world);
            world.addEntity(enemy);
        }

        for(Entity enemy : world.getEntitiesType(EntityType.ENEMY)){

            /*
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
            */
            Entity closestEntity = null;
            double closestDistance = Double.MAX_VALUE;
            //using euclidean distance to find whatever entity is closest to the enemy ship
            for(Entity entity : world.getEntities()){
                if(entity != enemy){
                    double dx = entity.getX() - enemy.getX();
                    double dy = entity.getY() - enemy.getY();
                    double distance = Math.sqrt(dx*dx + dy*dy);

                    if(distance < closestDistance){
                        closestEntity = entity;
                        closestDistance = distance;

                    }
                }
            }
            //targeting the closest entity to enemy, with a slight delay to the rotation
            //so it doesnt go full aimhacks
            //also dont aim at enemies cuz my enemy of my enemy is my friend eh? or something xd
            if(closestEntity != null && closestEntity.getEntityType() != EntityType.ENEMY && closestEntity.getEntityType() != EntityType.BULLET){
                double dx = closestEntity.getX() - enemy.getX();
                double dy = closestEntity.getY() - enemy.getY();
                double targetEntityAngle = Math.atan2(dy, dx) * (180/Math.PI);

                double rotationSpeed = 1.0;
                double currentAngle = enemy.getRotation();
                double angleDiff = targetEntityAngle - currentAngle;

                angleDiff = ((angleDiff % 360) + 540) % 360 - 180;

                if(Math.abs(angleDiff) > rotationSpeed){
                    double rotaDirect = Math.signum((angleDiff));
                    enemy.setRotation(currentAngle + rotationSpeed * rotaDirect);
                } else {
                    enemy.setRotation(targetEntityAngle);
                }

                double movementSpeed = 0.15;
                double distance = Math.sqrt(dx*dx + dy*dy);
                
                if (distance > 0){
                    enemy.setX(enemy.getX() + movementSpeed * dx / distance);
                    enemy.setY(enemy.getY() + movementSpeed * dy / distance);
                }
            }
        //Add shot delay
        if(Math.random()*100 > 99 && System.currentTimeMillis() - gameData.getLastShotTimer() >= shotDelayMilisec){
            getBulletSPIs().forEach(BulletSPI ->{
                Entity bullet = BulletSPI.createBullet(enemy, gameData);

                world.addEntity(bullet);
            });
            gameData.setLastShotTimer(System.currentTimeMillis());
            }
        }

    }

    private Collection<? extends BulletSPI> getBulletSPIs(){
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
