package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    /**
     * Create an asteroid.
     *
     * pre-condition :
     * GameData is not null
     * World is not null
     *
     * Post-condition
     * New asteroid entity is created
     *
     * @param gameData
     * @param world
     */
    Entity createAsteroid(GameData gameData, World world);
}
