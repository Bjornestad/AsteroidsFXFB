package dk.sdu.mmmi.cbse.playertest;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    private static Player player;
    private static World world;
    private static GameData gameData;


    @BeforeAll
    static void init() {
        player = new Player();
        world = new World();
        gameData = new GameData();
    }

    @BeforeEach
    void setUp() {
        world.addEntity(player);
    }

    @AfterEach
    void tearDown() {
        world.removeEntity(player);
    }

    @Test
    void movePlayerTest(){
        player.setX(10);
        player.setY(10);
        assertEquals(10, player.getX());
        assertEquals(10, player.getY());
    }

    @Test
    void turnPlayerTest(){
        player.setRotation(90);
        assertEquals(90, player.getRotation());
    }

    @Test
    void damagePlayerTest(){
        player.setHealth(5);
        player.setHealth(player.getHealth() - 1);
        assertEquals(4, player.getHealth());
    }
/*
//Not sure how to get this one to work
    @Test
    void playerShootTest(){
        for(int i = 0; i < 5; i++){
            getBulletSPIs().stream().findFirst().ifPresent(spi -> {
                world.addEntity(spi.createBullet(player, gameData));
            });
        }
        assertEquals(5, world.getEntities(Bullet.class).size());
    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

 */
}
