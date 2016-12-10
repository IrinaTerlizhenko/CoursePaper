package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DifferenceTest {
    @Test
    public void ABTest() {
        ArrayList<Point> fig1 = new ArrayList<>();
        fig1.add(new Point(0, 0));
        fig1.add(new Point(0, 10));
        fig1.add(new Point(10, 10));
        fig1.add(new Point(10, 0));

        Polygon firstPolygon = new Polygon(fig1);

        ArrayList<Point> fig2 = new ArrayList<>();
        fig2.add(new Point(5, 5));
        fig2.add(new Point(15, 5));
        fig2.add(new Point(15, 15));
        fig2.add(new Point(5, 15));

        Polygon secondPolygon = new Polygon(fig2);

        BinaryOperation binaryOperation = new BinaryOperation();
        List<Polygon> polygons = binaryOperation.figureDifference(firstPolygon, secondPolygon);

        for (Polygon polygon : polygons) {
            for (Point point : polygon.getPoints()) {
                System.out.print("(" + point.x + ", " + point.y + ") ->");
            }
            System.out.println();
        }
    }

    @Test
    public void BATest() {

        ArrayList<Point> fig2 = new ArrayList<>();
        fig2.add(new Point(5, 5));
        fig2.add(new Point(15, 5));
        fig2.add(new Point(15, 15));
        fig2.add(new Point(5, 15));

        ArrayList<Point> fig1 = new ArrayList<>();
        fig1.add(new Point(0, 0));
        fig1.add(new Point(0, 10));
        fig1.add(new Point(10, 10));
        fig1.add(new Point(10, 0));

        Polygon firstPolygon = new Polygon(fig1);

        Polygon secondPolygon = new Polygon(fig2);

        BinaryOperation binaryOperation = new BinaryOperation();
        List<Polygon> polygons = binaryOperation.figureDifference(secondPolygon, firstPolygon);

        for (Polygon polygon : polygons) {
            for (Point point : polygon.getPoints()) {
                System.out.print("(" + point.x + ", " + point.y + ") ->");
            }
            System.out.println();
        }
    }
}
