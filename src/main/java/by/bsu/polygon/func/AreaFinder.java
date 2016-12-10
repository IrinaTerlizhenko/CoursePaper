package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;

import java.util.List;

public class AreaFinder {
    private AreaFinder(){}

    /**
     * Method to find area of irregular polygon. O(n<sup>2</sup>) time
     * @param polygon polygon which area we need to find
     * @return Area of <code>polygon</code> with a sign. If return value < 0, then we go clockwise, counterclockwise otherwise.
     */
    public static double calculateArea(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        double xSum = 0.;
        double ySum = 0.;
        int size = polygon.length();
        for (int i = 0; i < size - 1; i++) {
            xSum += points.get(i).x * points.get(i + 1).y;
            ySum += points.get(i).y * points.get(i + 1).x;
        }
        xSum += points.get(size - 1).x * points.get(0).y;
        ySum += points.get(size - 1).y * points.get(0).x;
        return (xSum - ySum) / 2;
    }

    /**
     * Method to find area of irregular polygon. O(n<sup>2</sup>) time
     * @param points polygon which area we need to find
     * @return Area of <code>polygon</code> with a sign. If return value < 0, then we go clockwise, counterclockwise otherwise.
     */
    public static double calculateArea(List<Point> points) {
        double xSum = 0.;
        double ySum = 0.;
        int size = points.size();
        for (int i = 0; i < size - 1; i++) {
            xSum += points.get(i).x * points.get(i + 1).y;
            ySum += points.get(i).y * points.get(i + 1).x;
        }
        xSum += points.get(size - 1).x * points.get(0).y;
        ySum += points.get(size - 1).y * points.get(0).x;
        return (xSum - ySum) / 2;
    }
}

