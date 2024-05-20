package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     *
     *
     *
     * @param gameData
     * @param world
     *
     * Pre-condition: gameData not null, world not null
     * Post-condition : Classes implementing interface will have their process method called
     * updating them each game loop
     */
    void process(GameData gameData, World world);
}
