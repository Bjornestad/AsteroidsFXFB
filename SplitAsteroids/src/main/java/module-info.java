import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.splitAsteroid.AsteroidSplitControlSystem;

module SplitAsteroids {
    exports dk.sdu.mmmi.cbse.splitAsteroid;
    requires CommonAsteroids;
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.cbse.splitAsteroid.AsteroidSplitterPlugin;
    provides IEntityProcessingService with AsteroidSplitControlSystem;
    provides AsteroidSplitterSPI with AsteroidSplitControlSystem;


}