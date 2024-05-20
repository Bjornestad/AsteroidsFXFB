package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private double width;
    private double height;
    private EntityType type = EntityType.NONE;
    private boolean splitAble = false;
    private int health = 1;
    private long lastDamageTimer = 0;


    private boolean isPlayerAlive = true;

    public boolean isPlayerAlive() {
        return isPlayerAlive;
    }

    public void setPlayerAlive(boolean playerAlive) {
        isPlayerAlive = playerAlive;
    }

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
    public boolean isSplitAble() {
        return splitAble;
    }
    public void setSplitAble(boolean splitAble) {
        this.splitAble = splitAble;
    }
            

    public String getID() {
        return ID.toString();
    }


    public void setPolygonCoordinates(double... coordinates ) {
        this.polygonCoordinates = coordinates;

        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;

        for(int i = 0; i < this.polygonCoordinates.length; i+=2){
            double x = this.polygonCoordinates[i];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }

        this.width = maxX - minX;

        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for(int i = 1; i < this.polygonCoordinates.length; i+=2){
            double y = this.polygonCoordinates[i];
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        this.height = maxY - minY;

    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }
       

    public void setX(double x) {
        this.x =x;
    }

    public double getX() {
        return x;
    }

    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }


    public void setRadius(float radius) {
        this.radius = radius;
    }
    public float getRadius() {
        return radius;
    }
    public void setType(EntityType type) {
        this.type = type;
    }
    public EntityType getEntityType() {
        return type;
    }

    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public double getCenterX(){
        return this.x + (this.width/2);
    }
    public double getCenterY(){
        return this.y + (this.height/2);
    }
}
