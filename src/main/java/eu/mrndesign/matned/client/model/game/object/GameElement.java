package eu.mrndesign.matned.client.model.game.object;

import eu.mrndesign.matned.client.controller.TimeWrapper;
import eu.mrndesign.matned.client.model.tools.Bounds2D;
import eu.mrndesign.matned.client.model.tools.Vector2D;

import java.util.List;
import java.util.logging.Logger;

public abstract class GameElement {

    protected final Logger logger;

    protected final Vector2D referenceVector = new Vector2D(0, -10);

    protected int startFrame;

    protected String id;
    protected final CanvasModel canvasModel;
    protected Bounds2D bounds;
    protected final String name;
    protected boolean toRemove = false;

    protected int hP;
    protected final int hit;
    private final double speed;

    protected GameElement referenceElement;

    public GameElement(String name, double speed, Bounds2D bounds, CanvasModel canvasModel, int hP, int hit) {
        this.name = name;
        this.speed = speed;
        this.bounds = bounds;
        this.canvasModel = canvasModel;
        logger = Logger.getLogger(name);
        this.hP = hP;
        this.hit = hit;
        id = generateId();
    }

    public GameElement(String name, double speed, GameElement referenceElement , CanvasModel canvasModel, int hP, int hit) {
        this.name = name;
        this.speed = speed;
        this.referenceElement = referenceElement;
        this.canvasModel = canvasModel;
        logger = Logger.getLogger(name);
        this.hP = hP;
        this.hit = hit;
        id = generateId();
    }

    private String generateId(){
        return  name + System.currentTimeMillis();
    }

    public GameElement(String name, double speed, Bounds2D bounds, GameElement referenceElement, CanvasModel canvasModel, int hit, int hP) {
        this(name, speed, bounds, canvasModel, hP, hit);
        this.referenceElement = referenceElement;
        startFrame = (int) TimeWrapper.getInstance().getFrameNo();

    }

    public void moveTo(double x, double y) {
        rotate(x, y);
        move();
    }

    public void rotate(double x, double y) {
        bounds.rotate(x,y);
    }

    protected boolean isInBounds(GameElement gameElement) {
        return bounds.isCollisionWith(gameElement.bounds);
    }

    public void move() {
        bounds.getCenter().move(bounds.getVector(), speed);
    }

    public Vector2D getVector() {
        return bounds.getVector();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Bounds2D getBounds() {
        return bounds;
    }

    public void setVector(Vector2D vector) {
        bounds.setVector(vector);
    }

    public abstract List<String> frames();

    public abstract void refresh();

    public abstract void mouseMove(int x, int y);

    public abstract void action(int x, int y);

    public double getAngle() {
        return referenceVector.angleTo(bounds.getVector());
    }

    public abstract GameElementType getType();

    public abstract boolean isToRemove();

    public void setToRemove(){
        toRemove = true;
    }

    public int actualImageIndex(){
        return 0;
    }

    public boolean isAnimation(){
        return false;
    }

    public double getSpeed() {
        return speed;
    }

    public int getHP() {
        return hP;
    }

    public int getHit() {
        return hit;
    }

    public void hit(int pts){
        hP -= pts;
        if (hP <= 0) {
            setToRemove();
        }
    }

    @Override
    public String toString() {
        return "GameElement{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", referenceVector=" + referenceVector +
                ", bounds=" + bounds +
                '}';
    }
}
