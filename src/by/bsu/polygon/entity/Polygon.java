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
        Point prev = points.get(points.size() - 1);
        for (Point pt : points) {
            segments.add(new Segment(prev, pt));
            prev = pt;
        }
        return segments;
    }

    public int length() {
        return points.size();
    }
}
