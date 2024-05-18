package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * IGame Plugin service for starting and stopping services
 * Contract is that classes implementing this interface
 * will have to implement start() and stop()
 */
public interface IGamePluginService {
    /**
     *
     * @param gameData
     * @param world
     *
     * Pre-condition: gameData not null, world not null
     * Post-condition : Classes implementing interface will have their start method called
     * initializing the implemented class
     *
     */

    void start(GameData gameData, World world);
    /**
     *
     * @param gameData
     * @param world
     *
     * Pre-condition: gameData not null, world not null
     * Post-condition : Classes implementing interface will have their stop method called
     * stopping the implemented class and removing it
     */

    void stop(GameData gameData, World world);
}
