package eu.mrndesign.matned.client.model.tools;

import java.awt.geom.Line2D;

public class Line {

    private Point2D a;
    private Point2D b;

    public Line(Point2D a, Point2D b) {
        this.a = a;
        this.b = b;
    }

    public Line(Line copy) {
        this(new Point2D(copy.a), new Point2D(copy.b));
    }

    public Point2D lineLineIntersection(Line line)
    {
        // Line AB represented as a1x + b1y = c1
        double a1 = b.y - a.y;
        double b1 = a.x - b.x;
        double c1 = a1*(a.x) + b1*(a.y);

        // Line line represented as a2x + b2y = c2
        double a2 = line.b.y - line.a.y;
        double b2 = line.a.x - line.b.x;
        double c2 = a2*(line.a.x)+ b2*(line.a.y);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return null;
        }
        else
        {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;
            return new Point2D(x, y);
        }
    }

    public boolean segmentIntersect(Line lineB) {
        Line2D line1 = new Line2D.Float((float)a.x, (float)a.y, (float)b.x, (float)b.y);
        Line2D line2 = new Line2D.Float((float)lineB.a.x, (float)lineB.a.y, (float)lineB.b.x, (float)lineB.b.y);
        return line2.intersectsLine(line1);
    }
}
