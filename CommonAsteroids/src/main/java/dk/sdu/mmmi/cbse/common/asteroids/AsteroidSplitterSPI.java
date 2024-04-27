package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.List;

public interface AsteroidSplitterSPI {
    /**
     * Create a split asteroid.
     *
     * Not sure if the classwork assignment meant for all interfaces to be
     * documented like this, just doing to be safe
     *
     * pre condition :
     * Entity is not null
     * World is not null
     * Has to be an asteroid entity
     *
     * Post :
     * New asteroid entity is created but smaller than the one
     * it split from
     *
     * @param entity The asteroid entity to split.
     * @param world The world to create the split asteroid in.
     */
    List<Asteroid> createSplitAsteroid(Entity entity, World world);
}
