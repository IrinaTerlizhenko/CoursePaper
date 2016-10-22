package by.bsu.polygon.demo;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;
import by.bsu.polygon.util.IntPair;
import by.bsu.polygon.util.SelfIntersection;
import by.bsu.polygon.util.SweepLine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 * @date 22.10.2016
 */
public class Demo {
    public static void main(String[] args) {
        /*Point p1 = new Point(0.0, 1.0),
                p2 = new Point(1.0, 0.0),
                p3 = new Point(2.0, 1.0),
                p4 = new Point(3.0, 0.0);*/
        Point p1 = new Point(0.0, 1.0),
                p2 = new Point(1.0, 0.0),
                p3 = new Point(2.0, 1.0),
                p4 = new Point(3.0, 0.0),
                p5 = new Point(-1.0, 1.0);
        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        //points.add(p5);
        Polygon polygon = new Polygon(points);
        List<IntPair> intersections = SweepLine.solve(polygon);
        for (IntPair p : intersections) {
            System.out.println(p.first + " " + p.second);
        }

        SelfIntersection.arrangeWithoutIntersections(polygon, intersections);
    }
}
