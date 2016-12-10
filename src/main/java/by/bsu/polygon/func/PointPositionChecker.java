package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;

import java.util.List;

import static java.lang.Math.abs;

public class PointPositionChecker {
    private static final Point r = new Point(1, 0);
    private static final double EPS = 1e-9;

    public static boolean checkIfInside(Point point, Polygon figure) {
        int intersectNumber = 0;
        double x = point.x;
        double y = point.y;
        List<Point> points = figure.getPoints();
        for (int i = 0, size = points.size(); i < size; i++) {
            Point firstPoint, secondPoint;
            firstPoint = points.get(i);
            secondPoint = points.get((i + 1) % size);
            Point s = new Point(secondPoint.x - firstPoint.x,
                    secondPoint.y - firstPoint.y);
            Point difference = new Point(firstPoint.x - x,
                    firstPoint.y - y);
            double rs = scalarProduct(r, s);
            if (abs(rs) < EPS) {
                if (abs(firstPoint.y - point.y) > EPS) {
                    continue;
                } else if (firstPoint.x < point.x && secondPoint.x < point.x) {
                    continue;
                } else if (firstPoint.x < point.x ^ secondPoint.x < point.x) {
                    return true;
                } else {
                    Point left, right;
                    left = points.get((i + size - 1) % size);
                    right = points.get((i + 2) % size);
                    double d1 = (left.x - x) * r.y - (left.y - y) * r.x;
                    double d2 = (right.x - x) * r.y - (right.y - y) * r.x;
                    if (d1 * d2 < 0) {
                        ++intersectNumber;
                    }
                }
            } else {
                double t = scalarProduct(difference, s) / rs;
                double u = scalarProduct(difference, r) / rs;
                if (t >= 0 && 0 <= u && u <= 1) {
                    ++intersectNumber;
                    if (u == 0) {
                        Point left, right;
                        left = points.get((i + size - 1) % size);
                        right = points.get((i + 1) % size);
                        double d1 = (left.x - x) * r.y - (left.y - y) * r.x;
                        double d2 = (right.x - x) * r.y - (right.y - y) * r.x;
                        // If get to intersection point
                        System.out.println(d1);
                        System.out.println(d2);
                        if (d1 * d2 < 0) {
                            --intersectNumber;
                        }
                    }
                }
            }
        }
        return intersectNumber % 2 == 1;
    }

    private static double scalarProduct(Point v, Point w) {
        return v.x * w.y - v.y * w.x;
    }
}
