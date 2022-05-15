package eu.mrndesign.matned.client.model.tools;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    void angleBetweenVectorsShouldBe45() {
        Vector2D v1 = new Vector2D(5, 5);
        Vector2D v2 = new Vector2D(0, 5);
        assertEquals(45, v1.angleTo(v2), 0.001);
    }

    @Test
    void angleBetweenVectorsShouldBe135() {
        Vector2D v1 = new Vector2D(0, -5);
        Vector2D v2 = new Vector2D(5, 5);
        assertEquals(135, v1.angleTo(v2), 0.001);
    }

    @Test
    void angleBetweenVectorsShouldBe315() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(5, 5);
        assertEquals(315, v1.angleTo(v2), 0.001);
    }

    @Test
    void angleBetweenVectorsShouldBe90() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(5, 0);
        assertEquals(90, v2.angleTo(v1), 0.001);
    }

    @Test
    void angleBetweenVectorsShouldBe180() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(0, -5);
        assertEquals(180, v2.angleTo(v1), 0.001);
    }

    @Test
    void rotateVectorBy_90Degree() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(v1);

        v1.rotate(90);

        assertEquals(90, v1.angleTo(v2), 0.001);
    }

    @Test
    void rotateVectorBy_159Degree() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(v1);

        v1.rotate(159);

        assertEquals(159, v1.angleTo(v2), 0.001);
    }


    @Test
    void rotateVectorBy_288Degree() {
        Vector2D v1 = new Vector2D(0, 5);
        Vector2D v2 = new Vector2D(v1);

        v1.rotate(288);

        assertEquals(288, v1.angleTo(v2), 0.001);
    }

    @Test
    void rotateVectorBy_182Degree_differentCheck() {
        Vector2D v1 = new Vector2D(34, 5);
        assertEquals(34.36568, v1.magnitude(), 0.00001);
        v1.normalize();
        assertEquals(1, v1.magnitude(), 0.00001);
    }

    @Test
    void rotateVectorBy_182Degree_differentCheck_checkWithNegativeNumbers1() {
        Vector2D v1 = new Vector2D(-34, -5);
        assertEquals(34.36568, v1.magnitude(), 0.00001);
        v1.normalize();
        assertEquals(1, v1.magnitude(), 0.00001);
    }

    @Test
    void rotateVectorBy_182Degree_differentCheck_checkWithNegativeNumbers2() {
        Vector2D v1 = new Vector2D(-34, 5);
        assertEquals(34.36568, v1.magnitude(), 0.00001);
        v1.normalize();
        assertEquals(1, v1.magnitude(), 0.00001);
    }

    @Test
    void creatingConstructorFrom2PointsShouldMakeProperVectorXAndY() {
        Point2D p1 = new Point2D(2, 2);
        Point2D p2 = new Point2D(7, 7);

        Vector2D v = new Vector2D(p1, p2);

        assertEquals(5, v.getX(), 0.000001);
        assertEquals(5, v.getY(), 0.000001);
    }

    @Test
    void normalizedVectorLengthShouldBe_1() {
        Vector2D v = new Vector2D(7, 89);
        v.normalize();

        Point2D p1 = new Point2D(7, 7);
        Point2D p2 = new Point2D(2, 2);

        assertEquals(7.0710678, p1.distanceFrom(p2), 0.000001);
    }

    @Test
    void normalizeStaticMethodVectorLengthShouldBe_1andOriginalVectorShouldNotBeSet() {
        Vector2D v1 = new Vector2D(7, 10);
        Vector2D v2 = Vector2D.normalized(v1);

        assertEquals(v1.magnitude(), 12.2065, 0.0001);
        assertEquals(v2.magnitude(), 1, 0.000001);
    }

    @Test
    void dotProductOfTheVectorsShouldBeProper() {
        Vector2D v1 = new Vector2D(4, -4);
        Vector2D v2 = new Vector2D(3, 7);

        double properDotProduct = 2.8284;
        double properRealDotProduct = -2.8284;
        double doublePrecision = 0.0001;
        double dotProductMethodResult = v1.dot(v2);
        double realDotProductMethodResult = v1.realDot(v2);

        assertEquals(dotProductMethodResult, properDotProduct, doublePrecision);
        assertEquals(realDotProductMethodResult, properRealDotProduct, doublePrecision);
    }

    @Test
    void addVectors() {
        Vector2D v1 = new Vector2D(4, -4);
        Vector2D v2 = new Vector2D(3, 7);

        v1.add(v2);

        assertEquals(7, v1.getX(), 0.1);
        assertEquals(3, v1.getY(), 0.1);
        assertEquals(3, v2.getX(), 0.1);
        assertEquals(7, v2.getY(), 0.1);
    }


    @Test
    void subtractVectors() {
        Vector2D v1 = new Vector2D(4, -4);
        Vector2D v2 = new Vector2D(3, 7);

        v1.subtract(v2);

        assertEquals(1, v1.getX(), 0.1);
        assertEquals(-11, v1.getY(), 0.1);
        assertEquals(3, v2.getX(), 0.1);
        assertEquals(7, v2.getY(), 0.1);
    }

    @Test
    void multiplyVectors() {
        Vector2D v1 = new Vector2D(4, -4);
        Vector2D v2 = new Vector2D(3, 7);

        v1.multiply(v2);

        assertEquals(12, v1.getX(), 0.1);
        assertEquals(-28, v1.getY(), 0.1);
        assertEquals(3, v2.getX(), 0.1);
        assertEquals(7, v2.getY(), 0.1);
    }

    @Test
    void angleTo1(){
        Vector2D v2 = new Vector2D(3, 6);

        double result = v2.angleTo(-1,-2);

        assertEquals(result, 180, 0.1);
    }

    @Test
    void angleTo2(){
        Vector2D v2 = new Vector2D(3, 6);

        double result = v2.angleTo(4,-2);

        assertEquals(result, 270, 0.1);
    }


    @Test
    void angleTo3(){
        Point2D zero = new Point2D(6,2);
        Vector2D v1 = new Vector2D(zero, new Point2D(3,8));
        Vector2D v2 = new Vector2D(zero, new Point2D(6,4));

        assertNotEquals(v1.angleTo(v2), v2.angleTo(v1));

    }

    @Test
    void angleAfterRotation() {
        Vector2D v1 = new Vector2D(new Point2D(3, 8), new Point2D(0,11));
        Vector2D v2 = new Vector2D(new Point2D(3, 8), new Point2D(6,2));
        Vector2D vRotated = v1.rotated(90);

        System.out.println(v2.angleTo(vRotated));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"dotProduct.csv"}, useHeadersInDisplayName = true)
    void dotProductTests(double vX, double vY, double pX, double pY, double result){
        Vector2D v = new Vector2D(vX, vY);
        Point2D p = new Point2D(pX, pY);

        double dot = v.dot(p);

        assertEquals(dot, result, 0.001);
    }





}