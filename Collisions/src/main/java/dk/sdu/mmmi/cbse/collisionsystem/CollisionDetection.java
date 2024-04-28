package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.scene.shape.Polygon;


import java.util.*;

import static java.util.stream.Collectors.toList;

public class CollisionDetection implements IEntityProcessingService {

    private Map<Entity, Polygon> polygons;

    public CollisionDetection() {
    }

    public void setPolygons(Map<Entity, Polygon> polygons) {
        this.polygons = polygons;
    }


    @Override
    public void process(GameData gameData, World world) {
        //For some reason EntityType only works here for the checks and im not entirely sure why
        //So instanceof is used for the other ones, prob not good with jpms in mind
        //but i really cant figure it out
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                //First check if the entities are the same, if they are ignore collision
                if (e1.getEntityType() == e2.getEntityType() || !this.collide(e1, e2)) {
                    continue;
                }
                //Then check who shot the bullet, if the bullet was shot by the entity it is colliding with it ignores collision
                //This is mostly for the enemies as they tend to die to their own bullets
                if ((e1 instanceof Bullet && ((Bullet) e1).getShooter() == e2) ||
                        (e2 instanceof Bullet && ((Bullet) e2).getShooter() == e1)) {
                    continue;
                }
                if ((e1 instanceof Asteroid && e2 instanceof Bullet) ||
                        (e1 instanceof Bullet && e2 instanceof Asteroid)) {
                    Asteroid asteroid;
                    if(e1 instanceof Asteroid) {
                        asteroid = (Asteroid) e1;
                    } else {
                        asteroid = (Asteroid) e2;
                    }
                    if (this.collide(e1, e2) && asteroid.isSplitAble()) {
                        System.out.println("Splitable asteroid collided with bullet");
                        //Split asteroid on bullet hit
                        getAsteroidSplitterSPI().stream().findFirst().ifPresent(
                                spi -> {
                                    List<Asteroid> ray;
                                    ray = spi.createSplitAsteroid(asteroid, gameData);
                                    System.out.println("HIT");
                                    for(int i = 0; i < ray.size(); i++) {
                                        world.addEntity(ray.get(i));
                                    }
                                }
                        );
                    }
                }
                //If the entities are different and bullet was not shot by the entity it is colliding with
                //then remove the entities from thw world
                if (this.collide(e1, e2)) {
                    world.removeEntity(e1);
                    world.removeEntity(e2);
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
    private Collection<? extends AsteroidSplitterSPI> getAsteroidSplitterSPI(){
        return ServiceLoader.load(AsteroidSplitterSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
