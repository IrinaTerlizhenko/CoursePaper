package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author User
 * @date 28.10.2016
 */
public class DirectedIntersection {
    static List<TreeSet<IntersectionPoint>> findDirectedIntersections(Polygon polygon) {
        List<TreeSet<IntersectionPoint>> directedIntersections = new ArrayList<>();
        for (int i = 0; i < polygon.length(); i++) {
            directedIntersections.add(new TreeSet<>((o1, o2) -> {
                if (o1.x == o2.x) {
                    return 0;
                }
                Segment s = findEqualSegment(o1, o2);
                boolean o1Greater = (s.p.x < s.q.x) ^ (o1.x < o2.x);
                return o1Greater ? 1 : -1;
            }));
        }
        List<Segment> segmentList = polygon.toSegmentList();
        for (int i = 0; i < polygon.length(); i++) {
            findAllIntersections(segmentList.get(i), segmentList, directedIntersections, i);
        }
        return directedIntersections;
    }

    private static void findAllIntersections(
            Segment segment,
            List<Segment> segmentList,
            List<TreeSet<IntersectionPoint>> directedIntersections,
            int i) {
        for (int j = i + 2; j < segmentList.size(); j++) {
            if (i == 0 && j == segmentList.size() - 1) {
                continue;
            }
            Segment s = segmentList.get(j);
            if (Intersection.intersect(segment, s)) {
                IntersectionPoint intersectionPoint = new IntersectionPoint(Intersection.xIntersectionPoint(segment, s), segment, s);
                intersectionPoint.y = segment.get_y(intersectionPoint.x);
                directedIntersections.get(i).add(intersectionPoint);
                directedIntersections.get(j).add(intersectionPoint);
            }
        }
    }

    private static Segment findEqualSegment(IntersectionPoint p1, IntersectionPoint p2) {
        return (p1.s1.equals(p2.s1) || p1.s1.equals(p2.s2)) ? p1.s1 : p1.s2;
    }
}
