package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *  IPostEntityProcessingService for processing entities after they have been processed
 * @author jcs
 */
public interface IPostEntityProcessingService {
    /**
     *
     * @param gameData
     * @param world
     *
     * Pre-condition: gameData not null, world not null
     * Post-condition : Classes implementing interface will have their process method called
     * after IEntityProcessingService has been called on them
     */

    void process(GameData gameData, World world);
}
