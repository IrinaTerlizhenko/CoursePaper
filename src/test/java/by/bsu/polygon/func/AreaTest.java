package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;
import by.bsu.polygon.func.AreaFinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AreaTest {
    private static final double EPS = 1E-9;
    @Test
    public void parallelogramTest(){
        double expected = -4.;
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(5, 1));
        points.add(new Point(4, 0));
        Polygon polygon = new Polygon(points);
        double area = AreaFinder.calculateArea(polygon);
        Assert.assertEquals(expected, area, EPS);
    }

    @Test
    public void squareTest(){
        double expected = 9.;
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 0));
        points.add(new Point(3, 3));
        points.add(new Point(0, 3));
        Polygon polygon = new Polygon(points);
        double area = AreaFinder.calculateArea(polygon);
        Assert.assertEquals(expected, area, EPS);
    }
    @Test
    public void lineTest(){
        double expected = 9.;
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 0));
        points.add(new Point(3, 3));
        points.add(new Point(4, 7));
        points.add(new Point(3, 3));
        points.add(new Point(0, 3));
        Polygon polygon = new Polygon(points);
        double area = AreaFinder.calculateArea(polygon);
        Assert.assertEquals(expected, area, EPS);
    }
}
