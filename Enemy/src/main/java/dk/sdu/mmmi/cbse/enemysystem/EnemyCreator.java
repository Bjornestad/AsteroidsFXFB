package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.*;


public class EnemyCreator {
    public Enemy createEnemy(GameData gameData, World world){
        //sets all the values for the enemy
        //need different function if different enemy is wanted
        //or change to a function that loads from file
        //if it should be "easier" to create new enemies
        //so it just takes parameters from there
        Enemy enemy = new Enemy();
        enemy.setType(EntityType.ENEMY);
        enemy.setPolygonCoordinates(-10,-10,10,0,-10,10);
        enemy.setX(gameData.getDisplayHeight()*Math.random());
        enemy.setY(gameData.getDisplayWidth()*Math.random());
        enemy.setRotation(Math.random()*360);
        enemy.setHealth(3);

        return enemy;
    }
}
