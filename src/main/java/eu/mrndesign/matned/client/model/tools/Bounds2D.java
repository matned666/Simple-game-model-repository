package eu.mrndesign.matned.client.model.tools;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		List<Point2D> thisPoints = getPoints();
		List<Point2D> bPoints = b.getPoints();
		List<Vector2D> thisVectors = getVectors();
		List<Vector2D> bVectors = b.getVectors();
		if (thisVectors.stream().allMatch(v-> isNoGapOnShadow(v, thisPoints, bPoints))){
			return bVectors.stream().allMatch(v-> isNoGapOnShadow(v, bPoints, thisPoints));
		}
		return false;
	}

	private boolean isNoGapOnShadow(Vector2D v, List<Point2D> thisPoints, List<Point2D> bPoints) {
		List<Double> thisDots = thisPoints.stream().map(v::dot).collect(Collectors.toList());
		List<Double> bDots = bPoints.stream().map(v::dot).collect(Collectors.toList());
		double thisMin = thisDots.stream().min(Double::compareTo).get();
		double thisMax = thisDots.stream().max(Double::compareTo).get();
		double bMin = bDots.stream().min(Double::compareTo).get();
		double bMax = bDots.stream().max(Double::compareTo).get();
		return thisMin - bMax <= 0 && bMin - thisMax <= 0;
	}

	private List<Vector2D> getVectors() {
		return Arrays.asList(vector, vector.getPerpVector(), vector.getCounterVector(), vector.getCounterPerpVector());
	}

	private List<Point2D> getPoints() {
		return Arrays.asList(getTopLeft(), getTopRight(), getBottomLeft(), getBottomRight());
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
