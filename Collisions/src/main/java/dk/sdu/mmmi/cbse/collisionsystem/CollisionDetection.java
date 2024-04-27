package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidSplitter;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import javafx.scene.shape.Polygon;


import java.util.List;
import java.util.Map;

public class CollisionDetection implements IPostEntityProcessingService {

    private Map<Entity, Polygon> polygons;

    public CollisionDetection() {
    }

    public void setPolygons(Map<Entity, Polygon> polygons) {
        this.polygons = polygons;
    }


    @Override
    public void process(GameData gameData, World world) {
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                //First check if the entities are the same, if they are ignore collision
                if (e1.getEntityType() == e2.getEntityType()) {
                    continue;
                }
                //Then check who shot the bullet, if the bullet was shot by the entity it is colliding with it ignores collision
                if ((e1 instanceof Bullet && ((Bullet) e1).getShooter() == e2) ||
                        (e2 instanceof Bullet && ((Bullet) e2).getShooter() == e1)) {
                    continue;
                }
                if (this.collide(e1, e2)) {
                    // Check if e1 is a bullet and e2 is an asteroid
                    if (e1.getEntityType() == EntityType.BULLET && e2.getEntityType() == EntityType.ASTEROID && ((Asteroid) e2).isSplitAble()) {
                        world.removeEntity(e1);
                        world.removeEntity(e2);
                        AsteroidSplitter AstroidSplitter = new AsteroidSplitter();
                        // Create split asteroids and add them to the world
                        List<Asteroid> splitAsteroids = AstroidSplitter.createSplitAsteroid(gameData, e2.getX(), e2.getY(), e2.getRotation());
                        for (Asteroid asteroid : splitAsteroids) {
                            world.addEntity(asteroid);
                        }
                    }
                    // Check if e2 is a bullet and e1 is an asteroid
                    else if (e1.getType() == EntityType.ASTEROID && e2.getType() == EntityType.BULLET && ((Asteroid) e1).isSplitAble()) {
                        world.removeEntity(e1);
                        world.removeEntity(e2);
                        // Create split asteroids and add them to the world
                        List<Asteroid> splitAsteroids = asteroidCreator.createSplitAsteroid(gameData, e1.getX(), e1.getY(), e1.getRotation());
                        for (Asteroid asteroid : splitAsteroids) {
                            world.addEntity(asteroid);
                        }
                    }
                    //If the entities are different and bullet was not shot by the entity it is colliding with
                    //then remove said entities from thw world
                    if (this.collide(e1, e2)) {
                        world.removeEntity(e1);
                        world.removeEntity(e2);
                    }
                }
            }
        }
    }

    //this is based on a circle hitbox which will mean that it doesnt fit fully
    //but its something
    public boolean collide(Entity e1, Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < e1.getRadius() + e2.getRadius();
    }
}
