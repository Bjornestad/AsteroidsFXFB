package dk.sdu.mmmi.cbse.common.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface PlayerSPI {
    /**
     * Create a playable player.
     *
     * pre-condition :
     * GameData is not null
     *
     * Post-condition
     * New player entity is created
     *
     * @param gameData
     */


    Entity createPlayer(GameData gameData);
    /*
    * createPlayer for if the game could implement more players
    * or something that could play the game at once
    * would need change in how keybinds are done
    * but is not for now anyways
    *
    * could also use for respawn system in one way or another
     */
}
