package eu.mrndesign.matned.client.model.tools;

import java.util.Arrays;

import static eu.mrndesign.matned.client.controller.Constants.PANEL_HEIGHT_INT;
import static eu.mrndesign.matned.client.controller.Constants.PANEL_WIDTH_INT;

public class Bounds2D {

	protected Vector2D vector;
	protected double width;
	protected double height;
	protected final Point2D center;

	public Bounds2D(Vector2D vector, double width, double height, Point2D center) {
		this.width = width;
		this.height = height;
		this.center = center;
		this.vector = vector;
	}

	public boolean isOutOfView() {
		return center.x < 0 || center.x > PANEL_WIDTH_INT || center.y < 0 || center.y > PANEL_HEIGHT_INT;
	}

	public boolean touchedBy(Bounds2D b) {
        return isNoGapForBounds(b) && b.isNoGapForBounds(this);
    }

	private boolean isNoGapForBounds(Bounds2D b) {
		return isNoGapForVector(vector, b) && isNoGapForVector(getPerpVector(), b)
				&& isNoGapForVector(getCounterVector(), b) && isNoGapForVector(getCounterPerpVector(), b);
	}

	public boolean isNoGapForVector(Vector2D givenVector, Bounds2D b){
        Vector2D v = givenVector.newNormalized();
        double[] thisDots = new double[]{
            v.dot(getTopRight()),
            v.dot(getTopLeft()),
            v.dot(getBottomRight()),
            v.dot(getBottomLeft())
        };
        double[] bDots = new double[]{
            v.dot(b.getTopRight()),
            v.dot(b.getTopLeft()),
            v.dot(b.getBottomRight()),
            v.dot(b.getBottomLeft())
        };

		double thisMin = Arrays.stream(thisDots).min().getAsDouble();
		double thisMax = Arrays.stream(thisDots).max().getAsDouble();
		double bMin = Arrays.stream(bDots).min().getAsDouble();
		double bMax = Arrays.stream(bDots).max().getAsDouble();

		return thisMin - bMax <= 0 && bMin - thisMax <= 0;
    }

	public Point2D getTopRight() {
		return getCornerPointPoint(1, 1);
	}

	public Point2D getTopLeft() {
		Point2D result = new Point2D(center);
		result.move(vector, height/2);
		result.move(getPerpVector(), -width/2);
		return result;	}

	public Point2D getBottomRight() {
		return getCornerPointPoint(-1, 1);
	}

	public Point2D getBottomLeft() {
		return getCornerPointPoint(-1, -1);
	}

	public Point2D getCornerPointPoint(int modY, int modX) {
		Point2D result = new Point2D(center);
		result.move(vector, height/2 * modY);
		result.move(getPerpVector(), width/2 * modX);
		return result;
	}

	public Point2D getCenter() {
		return center;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setCenter(int x, int y) {
		center.setX(x);
		center.setY(y);
	}

	@Override
	public String toString() {
		return "Bounds2D{" + "width=" + width + ", height=" + height + '}';
	}

	public Vector2D getVector() {
		return vector;
	}

	public Vector2D getPerpVector() {
		Vector2D result = new Vector2D(vector);
		result.x = -vector.y;
		result.y = vector.x;
		return result;
	}

	public Vector2D getCounterVector() {
		Vector2D result = new Vector2D(vector);
		result.x *= -1;
		result.y *= -1;
		return result;
	}

	public Vector2D getCounterPerpVector() {
		Vector2D result = new Vector2D(vector);
		result.x = vector.y;
		result.y = -vector.x;
		return result;	}


	public void setVector(Vector2D vector) {
		this.vector = vector;
	}

	public void rotate(double x, double y) {
		this.vector = new Vector2D(center, x, y);
	}

}
