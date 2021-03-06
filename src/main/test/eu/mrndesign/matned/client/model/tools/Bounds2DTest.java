package eu.mrndesign.matned.client.model.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class Bounds2DTest {

	@ParameterizedTest
	@CsvSource({"0,0,1,0,10,10,false", "0,2000,1,0,10,10,true"})
	void Should_ReturnResultValue_When_OutOfBounds(double centerX, double centerY, double vectorX, double vectorY, double width, double height, boolean result){
		Point2D center = new Point2D(centerX, centerY);
		Vector2D vector = new Vector2D(vectorX, vectorY);
		Bounds2D bounds = new Bounds2D(vector, width, height, center);

		assertEquals(result, bounds.isOutOfView());
	}

	@Test
	void isTouched() {
		Vector2D vector = new Vector2D(0, 1);
		Bounds2D box = new Bounds2D(vector, 10, 10, new Point2D(0, 0));
		Bounds2D touch = new Bounds2D(vector, 4, 4, new Point2D(5, 0));
		assertTrue(box.isCollisionWith(touch), "try 1");

		touch.setCenter(4, -5);
		assertTrue(box.isCollisionWith(touch), "try 1");
	}

	@ParameterizedTest
	@CsvFileSource(resources = {"isTouchedTest.csv"}, useHeadersInDisplayName = true)
	void isTouchedTest(double aCenterX, double aCenterY, double aV1X, double aV1Y, double aV2X, double aV2Y,
					   double bCenterX, double bCenterY, double bV1X, double bV1Y, double bV2X, double bV2Y, boolean isTouched) {
		// given
		Point2D aCenter = new Point2D(aCenterX, aCenterY);
		Vector2D aV1 = new Vector2D(aV1X, aV1Y);
		Vector2D aV2 = new Vector2D(aV2X, aV2Y);
		Bounds2D a = new Bounds2D(aV1, aV2.magnitude()*2, aV1.magnitude()*2, aCenter);
		Point2D bCenter = new Point2D(bCenterX, bCenterY);
		Vector2D bV1 = new Vector2D(bV1X, bV1Y);
		Vector2D bV2 = new Vector2D(bV2X, bV2Y);
		Bounds2D b = new Bounds2D(bV1, bV2.magnitude()*2, bV1.magnitude()*2, bCenter);
		// when
		boolean conditionA = b.isCollisionWith(a);
		boolean conditionB = a.isCollisionWith(b);
		// then
		assertEquals(conditionA, isTouched);
		assertEquals(conditionB, isTouched);
	}

	@ParameterizedTest
	@CsvFileSource(resources = {"isTouchedTestSingle.csv"}, useHeadersInDisplayName = true)
	void isTouchedTestSingle(double aCenterX, double aCenterY, double aV1X, double aV1Y, double aV2X, double aV2Y,
							 double bCenterX, double bCenterY, double bV1X, double bV1Y, double bV2X, double bV2Y, boolean isTouched) {
		// given
		Point2D aCenter = new Point2D(aCenterX, aCenterY);
		Vector2D aV1 = new Vector2D(aV1X, aV1Y);
		Vector2D aV2 = new Vector2D(aV2X, aV2Y);
		Bounds2D a = new Bounds2D(aV1, aV2.magnitude()*2, aV1.magnitude()*2, aCenter);
		Point2D bCenter = new Point2D(bCenterX, bCenterY);
		Vector2D bV1 = new Vector2D(bV1X, bV1Y);
		Vector2D bV2 = new Vector2D(bV2X, bV2Y);
		Bounds2D b = new Bounds2D(bV1, bV2.magnitude()*2, bV1.magnitude()*2, bCenter);
		// when
		boolean conditionA = b.isCollisionWith(a);
		boolean conditionB = a.isCollisionWith(b);
		// then
		assertEquals(conditionA, isTouched);
		assertEquals(conditionB, isTouched);
	}

	@ParameterizedTest
	@CsvFileSource(resources = {"Bounds2DCornerPointsTest.csv"}, useHeadersInDisplayName = true)
	void cornerPointsTest(double vX, double vY, double cX, double cY, double h, double w, double tlX, double tlY, double trX, double trY, double brX, double brY, double blX, double blY){
		Vector2D boundsVector = new Vector2D(vX, vY);
		Point2D boundsCenter = new Point2D(cX, cY);
		Bounds2D bounds2D = new Bounds2D(boundsVector, w, h,boundsCenter);

		Point2D topLeftExpected = new Point2D(tlX, tlY);
		Point2D topRightExpected = new Point2D(trX, trY);
		Point2D bottomLeftExpected = new Point2D(blX, blY);
		Point2D bottomRightExpected = new Point2D(brX, brY);

		assertEquals(bounds2D.getTopLeft(), topLeftExpected, "topLeftExpected");
		assertEquals(bounds2D.getTopRight(), topRightExpected, "topRightExpected");
		assertEquals(bounds2D.getBottomLeft(), bottomLeftExpected, "bottomLeftExpected");
		assertEquals(bounds2D.getBottomRight(), bottomRightExpected, "bottomRightExpected");



	}



}