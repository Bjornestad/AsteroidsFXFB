package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import javafx.scene.shape.Polygon;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class CollisionDetection implements IPostEntityProcessingService{

    private Map<Entity, Polygon> polygons;
    private long lastPointUpdateTime;

    public CollisionDetection() {
    }

    public void setPolygons(Map<Entity, Polygon> polygons) {
        this.polygons = polygons;
    }

    /*
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
                    if ((e1 instanceof Asteroid && e2 instanceof Asteroid)) {
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
                            //Split asteroid on bullet hit
                            getAsteroidSplitterSPI().stream().findFirst().ifPresent(
                                    spi -> {
                                        List<Asteroid> ray;
                                        ray = spi.createSplitAsteroid(asteroid, gameData);
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
        */
    /*
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getEntityType() == e2.getEntityType() && this.collide(e1, e2)) {
                    handleSameEntity(e1, e2);
                } else if ((e1 instanceof Asteroid && e2 instanceof Asteroid) && this.collide(e1, e2)) {
                    handleAsteroidHit(e1, e2);
                } else if ((e1 instanceof Asteroid && e2 instanceof Bullet) || (e1 instanceof Bullet && e2 instanceof Asteroid) && this.collide(e1, e2)) {
                    handleAsteroidBulletHit(e1, e2, world, gameData);
                    handleSplitAsteroidBulletHit(e1, e2, world, gameData);
                } else if ((e1 instanceof Bullet && ((Bullet) e1).getShooter() == e2) && this.collide(e1, e2)) {
                    handleBulletIgnoreHit(e1, e2);
                } else if ((e2 instanceof Bullet && ((Bullet) e2).getShooter() == e1) && this.collide(e1, e2)) {
                    handleBulletIgnoreHit(e1, e2);
                } else if ((e1 instanceof Player || e1 instanceof Enemy) && (e2 instanceof Bullet) && this.collide(e1, e2) && ((Bullet) e2).getShooter() != e1) {
                    handleShipShot(e1, e2, world);
                } else if ((e2 instanceof Player || e2 instanceof Enemy) && (e1 instanceof Bullet) && this.collide(e1, e2) && ((Bullet) e1).getShooter() != e2) {
                    handleShipShot(e1, e2, world);
                } else if (this.collide(e1, e2)) {
                    handleCollision(e1, e2, world);
                }
            }
        }
    }
    */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getEntityType() == e2.getEntityType() && this.collide(e1, e2)) {
                    handleSameEntity(e1, e2);
                } else if ((e1.getEntityType() == EntityType.ASTEROID &&
                        e2.getEntityType() == EntityType.ASTEROID) && this.collide(e1, e2)) {
                    handleAsteroidHit(e1, e2);
                } else if (((e1.getEntityType() == EntityType.ASTEROID &&
                        e2.getEntityType() == EntityType.BULLET) || (e1.getEntityType() == EntityType.BULLET &&
                        e2.getEntityType() == EntityType.ASTEROID)) && this.collide(e1, e2)) {
                    handleAsteroidBulletHit(e1, e2, world, gameData);
                    handleSplitAsteroidBulletHit(e1, e2, world, gameData);
                } else if (((e1.getEntityType() == EntityType.PLAYER || e1.getEntityType() == EntityType.ENEMY) &&
                        e2.getEntityType() == EntityType.BULLET) && this.collide(e1, e2)) {
                    handleShipShot(e1, e2, world);
                } else if (((e2.getEntityType() == EntityType.PLAYER || e2.getEntityType() == EntityType.ENEMY) &&
                        e1.getEntityType() == EntityType.BULLET) && this.collide(e1, e2)) {
                    handleShipShot(e1, e2, world);
                } else if (this.collide(e1, e2)) {
                    handleCollision(e1, e2, world);
                }
            }
        }
    }

    //bunch of empty functions for handling different types of collisions
    //could add things to them later similar to how the asteroid bounces on the wall
    //also makes it somewhat more readable i think
    public void handleSameEntity(Entity e1, Entity e2) {
    }

    public void handleAsteroidHit(Entity e1, Entity e2) {
    }

    public void handleAsteroidBulletHit(Entity e1, Entity e2, World world, GameData gameData) {
            Entity splitAbleEntity;
            if (e1.isSplitAble()) {
                splitAbleEntity = e1;
            } else {
                splitAbleEntity = e2;
            }
            //Split asteroid on bullet hit
            getAsteroidSplitterSPI().stream().findFirst().ifPresent(
                    spi -> {
                        List<Entity> ray;
                        ray = spi.createSplitAsteroid(splitAbleEntity);
                        for (int i = 0; i < ray.size(); i++) {
                            world.addEntity(ray.get(i));
                        }
                    }
            );
            handleCollision(e1, e2, world);

    }

    public void handleSplitAsteroidBulletHit(Entity e1, Entity e2, World world, GameData gameData) {
        Entity splitAsteroid;
        if (e1.getEntityType() == EntityType.ASTEROID_SPLIT) {
            splitAsteroid = e1;
        } else {
            splitAsteroid = e2;
        }
        if (this.collide(e1, e2) && !splitAsteroid.isSplitAble()) {
            handleCollision(e1, e2, world);
        }
    }

    public void handleShipShot(Entity e1, Entity e2, World world) {
        Entity bullet, entity;
        if (e1.getEntityType() == EntityType.BULLET) {
            bullet = e1;
            entity = e2;
        } else {
            bullet = e2;
            entity = e1;
        }
        world.removeEntity(bullet);
        long systemTime = System.currentTimeMillis();
        if (systemTime - entity.getLastDamageTimer() >= 1000) {
            entity.setHealth(entity.getHealth() - 1);
            entity.setLastDamageTimer(systemTime);
        }
        if ((entity.getEntityType() == EntityType.PLAYER && entity.getHealth() <= 0) ||
                (entity.getEntityType() == EntityType.ENEMY && entity.getHealth() <= 0)) {
            world.removeEntity(entity);
            System.out.println("Ship died by damage");
        }
    }

    public void handleCollision(Entity e1, Entity e2, World world) {
        world.removeEntity(e1);
        world.removeEntity(e2);
        if (e1.getEntityType() == EntityType.PLAYER) {
            System.out.println("player deadge");
            e1.setPlayerAlive(false);
        } else {
            long systemTime = System.currentTimeMillis();
            if (systemTime - lastPointUpdateTime >= 1000) {
                addPointForHit(e1, e2);
                lastPointUpdateTime = systemTime;
            }
        }
    }

    public void addPointForHit(Entity e1, Entity e2) {
        if (e1.getEntityType() == EntityType.BULLET && e2.getEntityType() == EntityType.ASTEROID
                || e1.getEntityType() == EntityType.ASTEROID && e2.getEntityType() == EntityType.BULLET) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/score?amount=1"))
                    .build();
            try {
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
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

    private Collection<? extends AsteroidSplitterSPI> getAsteroidSplitterSPI() {
        return ServiceLoader.load(AsteroidSplitterSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
