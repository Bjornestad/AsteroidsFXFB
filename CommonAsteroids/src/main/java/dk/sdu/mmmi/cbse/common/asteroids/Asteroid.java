package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private boolean splitAble = true;

    public boolean isSplitAble() {
        return splitAble;
    }
    public void setSplitAble(boolean splitAble) {
        this.splitAble = splitAble;
    }
}
