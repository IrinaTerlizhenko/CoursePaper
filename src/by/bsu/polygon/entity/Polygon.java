package by.bsu.polygon.entity;

import java.util.List;

/**
 * Created by User on 19.10.2016.
 */
public class Polygon<T> {
    private List<Point2D<T>> points;

    public Polygon() {
    }

    public Polygon(List<Point2D<T>> points) {
        this.points = points;
    }

    public List<Point2D<T>> getPoints() {
        return points;
    }

    public void setPoints(List<Point2D<T>> points) {
        this.points = points;
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

        Polygon<?> polygon = (Polygon<?>) o;

        return points != null ? points.equals(polygon.points) : polygon.points == null;

    }

    @Override
    public int hashCode() {
        return points != null ? points.hashCode() : 0;
    }
}
