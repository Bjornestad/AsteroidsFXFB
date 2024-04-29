package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
    private int health = 3;
    private long lastDamageTimer = 0;

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
}
