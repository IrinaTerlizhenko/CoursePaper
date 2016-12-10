package by.bsu.polygon.algorithm;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class BentleyOttmann {

    private BentleyOttmann() {
    }

    public static Set<Point> intersections(Set<Segment> segments) {
        return intersectionsMap(segments).keySet();
    }

    public static Map<Point, Set<Segment>> intersectionsMap(Set<Segment> segments) {

        return new HashMap<>();
    }

    protected static Set<Point> intersectionsNaive(Set<Segment> segments) {
        return new HashSet<>();
    }

}
