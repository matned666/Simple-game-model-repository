package eu.mrndesign.matned.client.model.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class Bounds2DTest {

	@Test
	void isTouched() {
		Vector2D vector = new Vector2D(0, 1);
		Bounds2D box = new Bounds2D(vector, 10, 10, new Point2D(0, 0));
		Bounds2D touch = new Bounds2D(vector, 4, 4, new Point2D(5, 0));
		assertTrue(box.touchedBy(touch), "try 1");

		touch.setCenter(4, -5);
		assertTrue(box.touchedBy(touch), "try 1");
	}

	@Test
	void anotherTouchedTest() {
		Point2D aCenter = new Point2D(3, 8);
		Point2D aV1 = new Point2D(0, 11);
		Point2D aV2 = new Point2D(5, 10);
		Vector2D vAHeight = new Vector2D(aCenter, aV1);
		Bounds2D a = new Bounds2D(vAHeight, aCenter.distanceFrom(aV1), aCenter.distanceFrom(aV2), aCenter);

		Point2D bCenter = new Point2D(6, 2);
		Point2D bV1 = new Point2D(2, 2);
		Point2D bV2 = new Point2D(6, 4);
		Vector2D vBHeight = new Vector2D(aCenter, aV1);
		Bounds2D b = new Bounds2D(vBHeight, bCenter.distanceFrom(bV1), bCenter.distanceFrom(bV2), bCenter);

		assertTrue(b.touchedBy(a), "a");
		assertTrue(a.touchedBy(b), "b");
	}

	@ParameterizedTest
	@CsvFileSource(resources = {"isTouchedTest.csv"}, useHeadersInDisplayName = true)
	void isTouchedTest(double aCenterX, double aCenterY, double aV1X, double aV1Y, double aV2X, double aV2Y,
					   double bCenterX, double bCenterY, double bV1X, double bV1Y, double bV2X, double bV2Y, boolean isTouched) {
		// given
		Point2D aCenter = new Point2D(aCenterX, aCenterY);
		Point2D aV1 = new Point2D(aV1X, aV1Y);
		Point2D aV2 = new Point2D(aV2X, aV2Y);
		Vector2D vAHeight = new Vector2D(aCenter, aV1);
		Bounds2D a = new Bounds2D(vAHeight, aCenter.distanceFrom(aV1), aCenter.distanceFrom(aV2), aCenter);
		Point2D bCenter = new Point2D(bCenterX, bCenterY);
		Point2D bV1 = new Point2D(bV1X, bV1Y);
		Point2D bV2 = new Point2D(bV2X, bV2Y);
		Vector2D vBHeight = new Vector2D(aCenter, aV1);
		Bounds2D b = new Bounds2D(vBHeight, bCenter.distanceFrom(bV1), bCenter.distanceFrom(bV2), bCenter);
		// when
		boolean conditionA = b.touchedBy(a);
		boolean conditionB = a.touchedBy(b);
		// then
		assertEquals(conditionA, isTouched);
		assertEquals(conditionB, isTouched);
	}

}