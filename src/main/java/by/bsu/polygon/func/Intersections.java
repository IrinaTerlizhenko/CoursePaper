package by.bsu.polygon.func;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static java.lang.Math.abs;

public class Intersections {

    private static final double EPS = 1e-9;

    public static HashMap<Point, TreeSet<IntersectionPoint>> findIntersections(List<Segment> seg1, List<Segment> seg2, HashMap<IntersectionPoint, Object> points) {
        HashMap<Point, TreeSet<IntersectionPoint>> answer = new HashMap<>();
        Optional<IntersectionPoint> intersectionPoint;
        IntersectionPoint ins;
        for (Segment segment1 : seg1) {
            for (Segment segment2 : seg2) {
                intersectionPoint = findIntersection(segment1, segment2);
                if (intersectionPoint.isPresent()) {
                    TreeSet<IntersectionPoint> intersectionPoints1 = answer.get(segment1.p);
                    TreeSet<IntersectionPoint> intersectionPoints2 = answer.get(segment2.p);
                    if (intersectionPoints1 == null) {
                        intersectionPoints1 = new TreeSet<>((o1, o2) -> Double.compare(o1.t, o2.t));
                    }
                    if (intersectionPoints2 == null) {
                        intersectionPoints2 = new TreeSet<>((o1, o2) -> Double.compare(o1.u, o2.u));
                    }
                    ins = intersectionPoint.get();
                    intersectionPoints1.add(ins);
                    intersectionPoints2.add(ins);
                    points.put(ins, null);
                    answer.put(segment1.p, intersectionPoints1);
                    answer.put(segment2.p, intersectionPoints2);
                }
            }
        }

        return answer;
    }

    public static Optional<IntersectionPoint> findIntersection(Segment s1, Segment s2) {
        Point r = new Point(s1.q.x - s1.p.x, s1.q.y - s1.p.y);
        Point s = new Point(s2.q.x - s2.p.x, s2.q.y - s2.p.y);
        Point difference = new Point(s2.p.x - s1.p.x,
                s2.p.y - s1.p.y);
        double rs = scalarProduct(r, s);
        if (abs(rs) < EPS) {
            if (abs(scalarProduct(difference, r)) < EPS) {
                double t0 = dot(difference, r) / dot(r, r);
                double t1 = t0 + dot(s, r) / dot(r, r);
                if (0 <= t0 && t0 <= 1 || 0 <= t1 && t1 <= 1) {
                    //TODO: TWO POINTS
                    IntersectionPoint ip1 = new IntersectionPoint();
                    IntersectionPoint ip2 = new IntersectionPoint();
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        } else {
            double t = scalarProduct(difference, s) / rs;
            double u = scalarProduct(difference, r) / rs;
            if (0 <= t && t <= 1 && 0 <= u && u <= 1) {
                double x = s1.p.x + t * r.x;
                double y = s1.p.y + t * r.y;
                return Optional.of(new IntersectionPoint(x, y, s1.q, s2.q, s1, s2, t, u));
            }
        }
        return Optional.empty();
    }

    private static double scalarProduct(Point v, Point w) {
        return v.x * w.y - v.y * w.x;
    }

    private static double dot(Point v, Point w) {
        return v.x * w.x + v.y * w.y;
    }
}
