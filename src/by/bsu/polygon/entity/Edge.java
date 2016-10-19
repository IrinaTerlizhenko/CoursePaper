package by.bsu.polygon.entity;

/**
 * Created by User on 19.10.2016.
 */
public class Edge<T> {
    private Point2D<T> start;
    private Point2D<T> end;

    public Edge() {
    }

    public Edge(Point2D<T> start, Point2D<T> end) {
        this.start = start;
        this.end = end;
    }

    public Point2D<T> getStart() {
        return start;
    }

    public void setStart(Point2D<T> start) {
        this.start = start;
    }

    public Point2D<T> getEnd() {
        return end;
    }

    public void setEnd(Point2D<T> end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (start != null ? !start.equals(edge.start) : edge.start != null) return false;
        return end != null ? end.equals(edge.end) : edge.end == null;

    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
