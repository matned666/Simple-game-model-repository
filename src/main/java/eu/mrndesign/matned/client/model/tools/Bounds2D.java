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

	public boolean isCollisionWith(Bounds2D b) {
		Point2D thisP1 = getTopLeft();
		Point2D thisP2 = getTopRight();
		Point2D thisP3 = getBottomLeft();
		Point2D thisP4 = getBottomRight();
		Point2D bP1 = b.getTopLeft();
		Point2D bP2 = b.getTopRight();
		Point2D bP3 = b.getBottomLeft();
		Point2D bP4 = b.getBottomRight();
		Vector2D thisPerpV = vector.getPerpVector();
		Vector2D thisCounterV = vector.getCounterVector();
		Vector2D thisCounterPerpV = vector.getCounterPerpVector();
		Vector2D bPerpV = b.vector.getPerpVector();
		Vector2D bCounterV = b.vector.getCounterVector();
		Vector2D bCounterPerpV = b.vector.getCounterPerpVector();
		return isNoGapForBounds(thisPerpV, thisCounterV, thisCounterPerpV, thisP1, thisP2, thisP3, thisP4, bP1, bP2,
				bP3, bP4)
				&& b.isNoGapForBounds(bPerpV, bCounterV, bCounterPerpV, bP1, bP2, bP3, bP4, thisP1, thisP2, thisP3,
						thisP4);
	}

	private boolean isNoGapForBounds(Vector2D perpV, Vector2D counterV, Vector2D counterPerpV, Point2D thisP1,
			Point2D thisP2, Point2D thisP3, Point2D thisP4, Point2D bP1, Point2D bP2, Point2D bP3, Point2D bP4) {
		return isNoGapForVector(vector, thisP1, thisP2, thisP3, thisP4, bP1, bP2, bP3, bP4)
				&& isNoGapForVector(perpV, thisP1, thisP2, thisP3, thisP4, bP1, bP2, bP3, bP4)
				&& isNoGapForVector(counterV, thisP1, thisP2, thisP3, thisP4, bP1, bP2, bP3, bP4)
				&& isNoGapForVector(counterPerpV, thisP1, thisP2, thisP3, thisP4, bP1, bP2, bP3, bP4);
	}

	private boolean isNoGapForVector(Vector2D givenVector, Point2D thisP1, Point2D thisP2, Point2D thisP3,
			Point2D thisP4, Point2D bP1, Point2D bP2, Point2D bP3, Point2D bP4) {
		Vector2D v = givenVector.newNormalized();
		double[] thisDots = new double[]{v.dot(thisP1), v.dot(thisP2), v.dot(thisP3), v.dot(thisP4)};
		double[] bDots = new double[]{v.dot(bP1), v.dot(bP2), v.dot(bP3), v.dot(bP4)};

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
		return getCornerPointPoint(1, -1);
	}

	public Point2D getBottomRight() {
		return getCornerPointPoint(-1, 1);
	}

	public Point2D getBottomLeft() {
		return getCornerPointPoint(-1, -1);
	}

	public Point2D getCornerPointPoint(int modY, int modX) {
		Point2D result = new Point2D(center);
		result.move(vector, height / 2 * modY);
		result.move(vector.getPerpVector(), width / 2 * modX);
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

	public void setVector(Vector2D vector) {
		this.vector = vector;
	}

	public void rotate(double x, double y) {
		this.vector = new Vector2D(center, x, y);
	}

}
