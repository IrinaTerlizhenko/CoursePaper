package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;
import by.bsu.polygon.input.PolygonReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author User
 * @date 28.10.2016
 */
public class IntersectionSequenceTest {
    @Test
    public void pointsWithIntersectionsInDirection() throws IOException {
        PolygonReader reader = new PolygonReader("/input.txt");
        Polygon polygon = reader.read();
        List<Point> expected = new ArrayList<>();
        Point p1 = new Point(0, 1),
                p2 = new Point(0, 0),
                p3 = new Point(1, 1),
                p4 = new Point(1, 0);
        Segment s1 = new Segment(p2, p3),
                s2 = new Segment(p4, p1);
        s1.id = 3;
        s2.id = 5;
        expected.add(p1);
        expected.add(p2);
        expected.add(new IntersectionPoint(0.5, 0.5, s1, s2));
        expected.add(p3);
        expected.add(p4);
        expected.add(new IntersectionPoint(0.5, 0.5, s1, s2));
        List<PriorityQueue<IntersectionPoint>> directedIntersections = DirectedIntersection.findDirectedIntersections(polygon);
        List<Point> points = new ArrayList<>();
        IntersectionSequence.pointsWithIntersectionsInDirection(polygon, directedIntersections, points, new ArrayList<>());
        Assert.assertEquals(expected, points);
    }
}
