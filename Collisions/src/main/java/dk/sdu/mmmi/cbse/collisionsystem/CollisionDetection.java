package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import javafx.scene.shape.Polygon;


import java.util.Map;

public class CollisionDetection implements IPostEntityProcessingService {

    private Map<Entity, Polygon> polygons;

    public CollisionDetection() {
    }

    public void setPolygons(Map<Entity, Polygon> polygons) {
        this.polygons = polygons;
    }


    @Override
    public void process(GameData gameData, World world) {
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getEntityType() == e2.getEntityType()) {
                    continue;
                }
                if (this.collide(e1, e2)) {
                    world.removeEntity(e1);
                    world.removeEntity(e2);
                }
            }
        }
    }

    //this is based on a circle hitbox which will mean that it doesnt fit fully
    //but its something
    public boolean collide(Entity e1, Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < e1.getRadius() + e2.getRadius();
    }
}
