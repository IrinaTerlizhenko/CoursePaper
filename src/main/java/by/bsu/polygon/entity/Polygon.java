package by.bsu.polygon.entity;

import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Irina Terlizhenko
 * @date 19.10.2016
 */
public class Polygon {
    private static long id = 1;
    private List<Point> points;
    public long figureId;


    public Polygon() {
        points = new ArrayList<>();
        figureId = id++;
    }

    public Polygon(List<Point> points) {
        figureId = id++;
        // Назначение потомка
        List<Point> pointList = new ArrayList<>();
        int size = points.size();
        if (size >= 3) {
            for (int i = 0; i < size - 1; i++) {
                Point point = points.get(i);
                point.next1 = points.get(i + 1);
                point.figureId = figureId;
                pointList.add(point);
            }
            Point point = points.get(size - 1);
            point.next1 = points.get(0);
            point.figureId = figureId;
            pointList.add(point);
            this.points = pointList;
        } else {
            this.points = new ArrayList<>();
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        // Назначение потомка
        List<Point> pointList = new ArrayList<>();
        int size = points.size();
        if (size >= 3) {
            for (int i = 0; i < size - 1; i++) {
                Point point = points.get(i);
                point.next1 = points.get(i + 1);
                point.figureId = figureId;
                pointList.add(point);
            }
            Point point = points.get(size - 1);
            point.next1 = points.get(0);
            point.figureId = figureId;
            pointList.add(point);
            this.points = pointList;
        } else {
            this.points = new ArrayList<>();
        }
    }

    public List<Segment> toSegmentList() {
        List<Segment> segments = new ArrayList<>();
        int size = length();
        if (size >= 3) {
            for (int i = 0; i < size - 1; i++) {
                segments.add(new Segment(points.get(i), points.get(i + 1)));
            }
            segments.add(new Segment(points.get(size - 1), points.get(0)));
            return segments;
        } else {
            return new ArrayList<>();
        }
    }

    public int length() {
        return points.size();
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygon = (Polygon) o;

        return points != null ? points.equals(polygon.points) : polygon.points == null;

    }

    @Override
    public int hashCode() {
        return points != null ? points.hashCode() : 0;
    }
}
