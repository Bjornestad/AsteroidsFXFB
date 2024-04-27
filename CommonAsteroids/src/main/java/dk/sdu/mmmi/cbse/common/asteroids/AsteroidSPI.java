package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    Asteroid createAsteroid(GameData gameData, World world);
}
