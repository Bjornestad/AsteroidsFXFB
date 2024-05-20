package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */
public interface BulletSPI {
    /**
     * Pre-condition: shooter is not null,and is enemy or player
     * Post-condition: New bullet entity is created
     * @param shooter
     * @param gameData
     * @return
     */
    Entity createBullet(Entity shooter, GameData gameData);
}
