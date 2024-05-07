package dk.sdu.mmmi.cbse.collisiontest;

import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {
    private static World world;
    private static Player player;
    private static Enemy enemy;
    private static Bullet bullet;
    private static CollisionDetection collisionDetection;

    @BeforeAll
    static void init() {
        world = new World();
        player = new Player();
        enemy = new Enemy();
        bullet = new Bullet();
        collisionDetection = new CollisionDetection();
    }
    @BeforeEach
    void setUp() {
        world.addEntity(player);
        world.addEntity(enemy);
        world.addEntity(bullet);
    }
    @AfterEach
    void tearDown() {
        world.removeEntity(player);
        world.removeEntity(enemy);
        world.removeEntity(bullet);
    }
    @Test
    void testPlayerEnemyCollision() {
        player.setX(10);
        player.setY(10);
        enemy.setX(10);
        enemy.setY(10);
        player.setRadius(10);
        enemy.setRadius(10);
        assertTrue(collisionDetection.collide(player, enemy));
    }
    @Test
    void testPlayerBulletCollision() {
        player.setX(10);
        player.setY(10);
        bullet.setX(10);
        bullet.setY(10);
        player.setRadius(10);
        bullet.setRadius(10);
        assertTrue(collisionDetection.collide(player, bullet));
    }
    @Test
    void testShipsNotColliding() {
        player.setX(10);
        player.setY(10);
        enemy.setX(100);
        enemy.setY(100);
        player.setRadius(10);
        enemy.setRadius(10);
        assertFalse(collisionDetection.collide(player, enemy));
    }
    @Test
    void testPlayerBulletNotColliding() {
        player.setX(10);
        player.setY(10);
        bullet.setX(100);
        bullet.setY(100);
        player.setRadius(10);
        bullet.setRadius(10);
        assertFalse(collisionDetection.collide(player, bullet));
    }
    @Test
    void testBulletHittingNonOwnPlayerShip(){
        player.setHealth(3);
        player.setLastDamageTimer(System.currentTimeMillis()-2000);
        collisionDetection.handleShipShot(player, bullet, world);
        assertEquals(2, player.getHealth());
    }

    @Test
    void testShipDying(){
        player.setHealth(1);
        player.setLastDamageTimer(System.currentTimeMillis()-2000);
        //Player type wasnt set to player, am i dumb or did i forgor something?
        player.setType(EntityType.PLAYER);
        collisionDetection.handleShipShot(player, bullet, world);
        assertTrue(world.getEntities(Player.class).isEmpty());
    }
}
