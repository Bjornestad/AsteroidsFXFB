import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module SplitAsteroids {
    exports dk.sdu.mmmi.cbse.splitAsteroid;
    requires CommonAsteroids;
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.cbse.splitAsteroid.AsteroidSplitterPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.splitAsteroid.AsteroidSplitMovement;
    provides AsteroidSplitterSPI with dk.sdu.mmmi.cbse.splitAsteroid.AsteroidSplitMovement;


}