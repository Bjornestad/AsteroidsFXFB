package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface EnemySPI {
     /**
      * Create an enemy.
      *
      * pre-condition :
      * GameData is not null
      * World is not null
      *
      * Post-condition
      * New enemy entity is created
      * @param gameData
      * @param world
      * @return
      */
     Entity createEnemy(GameData gameData, World world);
     /*thought about implementing like a mothership
     * that could spawn more enemies
     * but not for now
     */
}
