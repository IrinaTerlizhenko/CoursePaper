package by.bsu.polygon.func;

import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;
import org.junit.Test;

public class IntersectionTest {
    @Test
    public void collinearTest() {
        IntersectionPoint intersectionPoint = Intersections.findIntersection(new Segment(new Point(0, 0), new Point(10, 0)), new Segment(new Point(30, 0), new Point(8, 0))).orElse(new IntersectionPoint());
        System.out.println(intersectionPoint);
    }

    @Test
    public void wrongTest() {
        IntersectionPoint intersectionPoint = Intersections.findIntersection(new Segment(new Point(0, 0), new Point(10, 0)), new Segment(new Point(30, 0), new Point(8, 0))).orElse(new IntersectionPoint());
        System.out.println(intersectionPoint);
    }
}
