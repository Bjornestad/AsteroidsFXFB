import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Collisions {
    uses dk.sdu.mmmi.cbse.common.asteroids.AsteroidSplitterSPI;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    provides dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
    requires javafx.graphics;
    requires CommonBullet;
    requires CommonAsteroids;
    requires java.net.http;
    provides IGamePluginService with dk.sdu.mmmi.cbse.collisionsystem.CollisionPlugin;
}