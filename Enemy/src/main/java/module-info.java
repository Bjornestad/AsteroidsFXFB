import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Enemy {
    exports dk.sdu.mmmi.cbse.enemysystem;
    requires Common;
    requires CommonBullet;
    requires commonEnemy;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    uses dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;

}