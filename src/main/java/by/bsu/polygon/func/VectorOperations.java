package by.bsu.polygon.func;

import by.bsu.polygon.entity.point.Point;

import static java.lang.Math.abs;

public class VectorOperations {
    private static final double EPS = 1e-9;

    private VectorOperations(){

    }

    public static double crossProduct(Point origin, Point p, Point q) {
        double s = (p.x - origin.x) * (q.y - origin.y) - (p.y - origin.y) * (q.x - origin.x);
        return (abs(s) < EPS) ? 0 : s;
    }

    public static SideOfPoint checkSide(Point origin, Point p, Point q) {
        double s = (p.x - origin.x) * (q.y - origin.y) - (p.y - origin.y) * (q.x - origin.x);
        return (abs(s) < EPS) ? SideOfPoint.ON_LINE :
                ((s > 0) ? SideOfPoint.RIGHT :
                        SideOfPoint.LEFT);
    }
}
