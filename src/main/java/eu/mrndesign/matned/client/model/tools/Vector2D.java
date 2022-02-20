package eu.mrndesign.matned.client.model.tools;

public class Vector2D extends Point2D{

    public Vector2D(double x, double y) {
        super(x,y);
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D start, Point2D end) {
        this(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector2D(Vector2D copy) {
        this(copy.x, copy.y);
    }

    public Vector2D(Point2D center, double x, double y) {
        super(x - center.x, y - center.y);
    }

    public double angleTo(Vector2D v) {
        double dot = x*v.x + y*v.y;
        double det = x*v.y - y*v.x;
        double result = Math.toDegrees(Math.atan2(det, dot));
        if (result >= 0) {
            return result;
        } else {
            return 360 + result;
        }
    }

    public void normalize(){
        double length = Math.abs(Math.sqrt(x*x + y*y));
        x /= length;
        y /= length;
    }

    public static Vector2D normalized(Vector2D v){
        Vector2D result = new Vector2D(v);
        double magnitude = v.magnitude();
        result.x /= magnitude;
        result.y /= magnitude;
        return result;
    }

    public double angleTo(double x, double y) {
        Vector2D v = new Vector2D(x, y);
        return angleTo(v);
    }

    public void rotate(double angleDegrees){
        double oldX = x;
        x = (oldX * Math.cos(Math.toRadians(angleDegrees)) + y * Math.sin(Math.toRadians(angleDegrees)));
        y = (oldX * Math.sin(Math.toRadians(angleDegrees)) + y * Math.cos(Math.toRadians(angleDegrees)));
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }

    public void subtract(Vector2D v) {
        x -= v.x;
        y -= v.y;
    }

    public void multiply(Vector2D v) {
        x *= v.x;
        y *= v.y;
    }

    public double realDot(Vector2D v) {
        double magnitude = magnitude();
        return v.x * x / magnitude + v.y * y / magnitude;
    }

    public double dot(Vector2D v) {
        double magnitude = magnitude();
        return Math.abs(v.x * x / magnitude + v.y * y / magnitude);
    }

    public double magnitude(){
        return Math.sqrt(x*x + y*y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
