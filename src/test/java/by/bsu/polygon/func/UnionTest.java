package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UnionTest {

    @Test
    public void unionTest() {
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
        List<Polygon> polygons = binaryOperation.figureUnion(firstPolygon, secondPolygon);

        for (Polygon polygon : polygons) {
            for (Point point : polygon.getPoints()) {
                System.out.print("(" + point.x + ", " + point.y + ") ->");
            }
            System.out.println();
        }
    }

    @Test(timeout = 1000)
    public void holeTest() {
        ArrayList<Point> fig1 = new ArrayList<>();
        fig1.add(new Point(0, 0));
        fig1.add(new Point(0, 10000));
        fig1.add(new Point(10000, 10000));
        fig1.add(new Point(10000, 0));

        Polygon firstPolygon = new Polygon(fig1);

        ArrayList<Point> fig2 = new ArrayList<>();
        fig2.add(new Point(15000, 0));
        for (int i = 0; i <= 3000; i++) {
            if (i % 2 == 0) {
                fig2.add(new Point(7000, 1 + 2 * i));
            } else {
                fig2.add(new Point(13000, 1 + 2 * i));
            }
        }
        fig2.add(new Point(15000, 10000));

        Polygon secondPolygon = new Polygon(fig2);

        BinaryOperation binaryOperation = new BinaryOperation();
        List<Polygon> polygons = binaryOperation.figureUnion(firstPolygon, secondPolygon);

        for (Polygon polygon : polygons) {
            for (Point point : polygon.getPoints()) {
                System.out.print("(" + point.x + ", " + point.y + ") ->");
            }
            System.out.println();
        }
    }

    @Test
    public void rombusTest() {
        ArrayList<Point> fig1 = new ArrayList<>();
        fig1.add(new Point(0, 0));
        fig1.add(new Point(0, 10));
        fig1.add(new Point(10, 10));
        fig1.add(new Point(10, 0));

        Polygon firstPolygon = new Polygon(fig1);

        ArrayList<Point> fig2 = new ArrayList<>();
        fig2.add(new Point(5, -3));
        fig2.add(new Point(-3, 5));
        fig2.add(new Point(5, 13));
        fig2.add(new Point(13, 5));
        Polygon secondPolygon = new Polygon(fig2);

        BinaryOperation binaryOperation = new BinaryOperation();
        List<Polygon> polygons = binaryOperation.figureUnion(firstPolygon, secondPolygon);

        for (Polygon polygon : polygons) {
            for (Point point : polygon.getPoints()) {
                System.out.print("(" + point.x + ", " + point.y + ") ->");
            }
            System.out.println();
        }
    }
}
