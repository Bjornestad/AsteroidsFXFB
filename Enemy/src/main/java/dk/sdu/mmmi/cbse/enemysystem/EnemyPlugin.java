package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService, EnemySPI {
    private Entity enemy;

    public EnemyPlugin(){}
    @Override
    public void start(GameData gameData, World world) {
    }



    @Override
    public void stop(GameData gameData, World world) {
    }
    @Override
    public Entity createEnemy(GameData gameData, World world){
        //sets all the values for the enemy
        //need different function if different enemy is wanted
        //or change to a function that loads from file
        //if it should be "easier" to create new enemies
        //so it just takes parameters from there
        Entity enemy = new Entity();
        enemy.setType(EntityType.ENEMY);
        enemy.setPolygonCoordinates(-10,-10,10,0,-10,10);
        enemy.setX(gameData.getDisplayHeight()*Math.random());
        enemy.setY(gameData.getDisplayWidth()*Math.random());
        enemy.setRotation(Math.random()*360);
        enemy.setHealth(3);

        return enemy;
    }
}
