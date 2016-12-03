package by.bsu.polygon.util;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

import static java.lang.Math.*;

/**
 * @author User
 * @date 22.10.2016
 */
class Intersection {
    private static final double EPS = 1E-9;

    static boolean intersect(Segment a, Segment b) {
        return intersect1d(a.p.x, a.q.x, b.p.x, b.q.x)
                && intersect1d(a.p.y, a.q.y, b.p.y, b.q.y)
                && vec(a.p, a.q, b.p) * vec(a.p, a.q, b.q) <= 0
                && vec(b.p, b.q, a.p) * vec(b.p, b.q, a.q) <= 0;
    }

    private static boolean intersect1d(double l1, double r1, double l2, double r2) {
        if (l1 > r1) {
            double tmp = l1;
            l1 = r1;
            r1 = tmp;
        }
        if (l2 > r2) {
            double tmp = l2;
            l2 = r2;
            r2 = tmp;
        }
        return max(l1, l2) <= min(r1, r2) + EPS;
    }

    private static int vec(Point a, Point b, Point c) {
        double s = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return (abs(s) < EPS) ? 0 : ((s > 0) ? 1 : -1);
    }

    static double xIntersectionPoint(Segment a, Segment b) {
        Point p1 = a.p,
                p2 = a.q,
                p3 = b.p,
                p4 = b.q;
        return (p1.x * (p2.y - p1.y) / (p2.x - p1.x) - p1.y - p3.x * (p4.y - p3.y) / (p4.x - p3.x) + p3.y) /
                ((p2.y - p1.y) / (p2.x - p1.x) + (p3.y - p4.y) / (p4.x - p3.x));
    }
}
