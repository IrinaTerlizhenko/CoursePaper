package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author User
 * @date 22.10.2016
 */
public class SelfIntersection {
    public static void arrangeWithoutIntersections(Polygon polygon, List<IntPair> intersections) {
        List<Segment> segments = polygon.toSegmentList();
        List<Segment> newSegments = new ArrayList<>();
        for (IntPair inter : intersections) {
            int firstId = min(inter.first, inter.second);
            int secondId = max(inter.first, inter.second);
            Segment s1 = segments.get(firstId);
            Segment s2 = segments.get(secondId);
            newSegments.addAll(segments.subList(0, firstId));
            newSegments.add(firstHalf(s1, s2));
            newSegments.add(firstHalfReverse(s2, s1));
            newSegments.addAll(reverse(segments.subList(firstId + 1, secondId)));
            newSegments.add(secondHalfReverse(s1, s2));
            newSegments.add(secondHalf(s2, s1));
            newSegments.addAll(segments.subList(secondId + 1, segments.size()));
        }
        newSegments.stream().forEach(System.out::println);
    }

    private static List<Segment> reverse(List<Segment> segments) {
        return segments.stream().map(s -> new Segment(s.q, s.p)).collect(Collectors.toList());
    }

    private static Segment firstHalf(Segment seg, Segment other) {
        double x = Intersection.xIntersectionPoint(seg, other);
        return new Segment(seg.p, new Point(x, seg.get_y(x)));
    }

    private static Segment secondHalf(Segment seg, Segment other) {
        double x = Intersection.xIntersectionPoint(seg, other);
        return new Segment(new Point(x, seg.get_y(x)), seg.q);
    }

    private static Segment firstHalfReverse(Segment seg, Segment other) {
        double x = Intersection.xIntersectionPoint(seg, other);
        return new Segment(new Point(x, seg.get_y(x)), seg.p);
    }

    private static Segment secondHalfReverse(Segment seg, Segment other) {
        double x = Intersection.xIntersectionPoint(seg, other);
        return new Segment(seg.q, new Point(x, seg.get_y(x)));
    }
}
