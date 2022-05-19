package eu.mrndesign.matned.client.model.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CubicCurve2DTest {

    @Test
    public void shouldBeTheSameNoMatterOfSamplesNumber(){
        Point2D start = new Point2D(0,0);
        Point2D ctrl1 = new Point2D(2,3);
        Point2D ctrl2 = new Point2D(9,3);
        Point2D end = new Point2D(11,0);

        CubicCurve2D cubicCurve2D = new CubicCurve2D(start, ctrl1, ctrl2, end);

        assertEquals(cubicCurve2D.calculateLength(1000), cubicCurve2D.calculateLength(10), 0.1);
    }

    @Test
    public void shouldReturnProperLength(){
        Point2D start = new Point2D(0,0);
        Point2D ctrl1 = new Point2D(4,10);
        Point2D ctrl2 = new Point2D(17,10);
        Point2D end = new Point2D(22,0);

        CubicCurve2D cubicCurve2D = new CubicCurve2D(start, ctrl1, ctrl2, end);

        double actual1 = cubicCurve2D.calculateLength(4);
        double actual2 = cubicCurve2D.calculateLength(1000);

        assertEquals(24.5, actual1, 0.1);
        assertEquals(24.5, actual2, 0.1);
    }




}