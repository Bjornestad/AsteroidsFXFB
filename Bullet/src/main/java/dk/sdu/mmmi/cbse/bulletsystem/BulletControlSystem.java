package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 1.5);
            bullet.setY(bullet.getY() + changeY * 1.5);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        bullet.setPolygonCoordinates(0,0,-2,0,-4,2,-4,4,-2,6,0,6,2,4,2,2);
        //spawns the bullet in front of the shooter, as otherwise the shooter will collide with its own bullet

        double entityCenterX = shooter.getCenterX();
        double entityCenterY = shooter.getCenterY();
        //90 - getRotation for some javafx? spaghetti reason, not sure why tbh but it centers the bullet relative to the shooter
        double rotaRad = Math.toRadians(90 - shooter.getRotation());

        double dirX = Math.cos(rotaRad);
        double dirY = -Math.sin(rotaRad);

        double spawnDistanceOffset = 10;
        double offsetX = dirX + spawnDistanceOffset;
        double offsetY = dirY + spawnDistanceOffset;

        double cX = (entityCenterX + offsetX);
        double cY = (entityCenterY - offsetY);

        bullet.setX(cX);
        bullet.setY(cY);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(7);
        bullet.setShooter(shooter);
        bullet.setType(EntityType.BULLET);
        return bullet;
    }

}
