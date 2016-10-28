package by.bsu.polygon.util;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

/**
 * @author User
 * @date 22.10.2016
 */
public class Intersection {
    public static double xIntersectionPoint(Segment a, Segment b) {
        Point p1 = a.p,
                p2 = a.q,
                p3 = b.p,
                p4 = b.q;
        return (p1.x * (p2.y - p1.y) / (p2.x - p1.x) - p1.y - p3.x * (p4.y - p3.y) / (p4.x - p3.x) + p3.y) /
                ((p2.y - p1.y) / (p2.x - p1.x) + (p3.y - p4.y) / (p4.x - p3.x));
    }
}
