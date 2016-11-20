package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;
import by.bsu.polygon.input.PolygonReader;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author User
 * @date 08.11.2016
 */
public class IntersectionSplitterTest {
    @Test
    public void splitTest1() throws IOException {
        PolygonReader reader = new PolygonReader("/input3.txt");
        Polygon polygon = reader.read();
        List<PriorityQueue<IntersectionPoint>> directedIntersections = DirectedIntersection.findDirectedIntersections(polygon);
        List<Point> allPoints = new ArrayList<>();
        List<Segment> allSegments = new ArrayList<>();
        IntersectionSequence.pointsWithIntersectionsInDirection(polygon, directedIntersections, allPoints, allSegments);
        List<Polygon> polygons = new IntersectionSplitter().split(allPoints, allSegments);
        System.out.println(polygons);
    }

    @Test
    public void splitTest2() throws IOException {
        PolygonReader reader = new PolygonReader("/input4.txt");
        Polygon polygon = reader.read();
        List<PriorityQueue<IntersectionPoint>> directedIntersections = DirectedIntersection.findDirectedIntersections(polygon);
        List<Point> allPoints = new ArrayList<>();
        List<Segment> allSegments = new ArrayList<>();
        IntersectionSequence.pointsWithIntersectionsInDirection(polygon, directedIntersections, allPoints, allSegments);
        List<Polygon> polygons = new IntersectionSplitter().split(allPoints, allSegments);
        System.out.println(polygons);
    }
}
