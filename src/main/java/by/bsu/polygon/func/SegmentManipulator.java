package by.bsu.polygon.func;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.List;

public class SegmentManipulator {
    public static List<Segment> toSegmentList(List<Point> points) {
        List<Segment> segments = new ArrayList<>();
        int size = points.size();
        if (size >= 3) {
            for (int i = 0; i < size - 1; i++) {
                segments.add(new Segment(points.get(i), points.get(i + 1)));
            }
            segments.add(new Segment(points.get(size - 1), points.get(0)));
            return segments;
        } else {
            return new ArrayList<>();
        }
    }
}
