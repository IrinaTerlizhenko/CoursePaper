package by.bsu.polygon.entity;

import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Irina Terlizhenko
 * @date 19.10.2016
 */
public class Polygon {
    private List<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Segment> toSegmentList() {
        List<Segment> segments = new ArrayList<>();
        /*Point prev = points.get(points.size() - 1);
        for (Point pt : points) {
            segments.add(new Segment(prev, pt));
            prev = pt;
        }*/
        for (int i = 0; i < length() - 1; i++) {
            segments.add(new Segment(points.get(i), points.get(i + 1)));
        }
        segments.add(new Segment(points.get(points.size() - 1), points.get(0)));
        return segments;
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
