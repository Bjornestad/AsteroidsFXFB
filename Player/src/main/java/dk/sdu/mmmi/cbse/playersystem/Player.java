package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private int health = 3;
    private long lastDamageTimer = 0;



    private boolean isPlayerAlive = true;

    public long getLastDamageTimer() {
        return lastDamageTimer;
    }

    public void setLastDamageTimer(long lastDamageTimer) {
        this.lastDamageTimer = lastDamageTimer;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public boolean isPlayerAlive() {
        return isPlayerAlive;
    }

    public void setPlayerAlive(boolean playerAlive) {
        isPlayerAlive = playerAlive;
    }




}
