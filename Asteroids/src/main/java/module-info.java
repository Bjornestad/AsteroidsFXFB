import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidMovement;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroids {
    exports dk.sdu.mmmi.cbse.asteroidsystem;
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
    provides IEntityProcessingService with AsteroidMovement;


}