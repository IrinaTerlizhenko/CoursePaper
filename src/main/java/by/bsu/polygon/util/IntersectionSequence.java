package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;

import java.util.List;
import java.util.TreeSet;

/**
 * @author User
 * @date 28.10.2016
 */
public class IntersectionSequence {
    static List<Point> pointsWithIntersectionsInDirection(
            Polygon polygon,
            List<TreeSet<IntersectionPoint>> directedIntersections,
            List<Point> outPoints,
            List<Segment> outSegments) {
        List<Segment> segmentList = polygon.toSegmentList();

        for (int i = 0; i < polygon.length(); ++i) {
            outPoints.add(segmentList.get(i).p);
            outPoints.addAll(directedIntersections.get(i));
            if (directedIntersections.get(i).isEmpty()) {
                outSegments.add(segmentList.get(i));
            }
            Point prev = segmentList.get(i).p;
            int size = directedIntersections.get(i).size();
            while (!directedIntersections.get(i).isEmpty()) {
                Point pt = directedIntersections.get(i).first();
                directedIntersections.get(i).remove(pt);
                outSegments.add(new Segment(prev, pt));
                prev = pt;
            }
            if (size > 0) {
                outSegments.add(new Segment(prev, segmentList.get(i).q));
            }
        }
        return outPoints;
    }
}
