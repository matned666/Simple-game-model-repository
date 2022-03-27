package eu.mrndesign.matned.client.model.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Bounds2DTest {

    @Test
    void isSquareInBounds(){
        Vector2D vector = new Vector2D(0,1);
        Bounds2D bounds1 = new Bounds2D(vector, 5,5, new Point2D(0,0));
        Bounds2D bounds2 = new Bounds2D(vector, 2,2, new Point2D(1,1));

        assertTrue(bounds2.isIn(bounds1));
    }

    @Test
    void isSquareInBounds2(){
        Vector2D vector = new Vector2D(0,1);
        Bounds2D bounds1 = new Bounds2D(vector,5,5, new Point2D(0,0));
        Bounds2D bounds2 = new Bounds2D(vector,2,2, new Point2D(-1,1));

        assertTrue(bounds2.isIn(bounds1));
    }

    @Test
    void isTouched(){
        Vector2D vector = new Vector2D(0,1);
        Bounds2D box = new Bounds2D(vector, 10, 10, new Point2D(0, 0));
        Bounds2D touch = new Bounds2D(vector, 4, 4, new Point2D(5, 0));
        assertTrue(box.touchedBy(touch), "try 1");

        touch.setCenter(4,-5);
                assertTrue(box.touchedBy(touch), "try 1");
    }

    @Test
    void  anotherTouchedTest() {
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

    @Test
    void  notTouchedTest() {
        Point2D aCenter = new Point2D(3, 8);
        Point2D aV1 = new Point2D(0, 11);
        Point2D aV2 = new Point2D(5, 10);
        Vector2D vAHeight = new Vector2D(aCenter, aV1);
        Bounds2D a = new Bounds2D(vAHeight, aCenter.distanceFrom(aV1), aCenter.distanceFrom(aV2), aCenter);

        Point2D bCenter = new Point2D(16, 2);
        Point2D bV1 = new Point2D(12, 2);
        Point2D bV2 = new Point2D(16, 4);
        Vector2D vBHeight = new Vector2D(aCenter, aV1);
        Bounds2D b = new Bounds2D(vBHeight, bCenter.distanceFrom(bV1), bCenter.distanceFrom(bV2), bCenter);

        assertFalse(b.touchedBy(a), "a");
        assertFalse(a.touchedBy(b), "b");
    }






}