import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collisions {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
}