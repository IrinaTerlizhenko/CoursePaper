package by.bsu.polygon.entity;

import by.bsu.polygon.entity.point.Point;

import static java.lang.Math.*;

/**
 * @author User
 * @date 22.10.2016
 */
public class Segment implements Comparable<Segment> {
    private static final double EPS = 1E-9;

    private static int id_generator = 0;

    public Point p, q;
    public int id;

    public Segment() {
        id = id_generator++;
    }

    public Segment(Point p, Point q) {
        this();
        this.p = p;
        this.q = q;
    }

    public double get_y(double x) {
        if (abs(p.x - q.x) < EPS) return p.y;
        return p.y + (q.y - p.y) * (x - p.x) / (q.x - p.x);
    }

    public boolean isRight(Point pt) {
        return (pt.x - p.x) * (q.y - p.y) - (pt.y - p.y) * (q.x - p.x) > 0;
    }

    public boolean isLeft(Point pt) {
        return (pt.x - p.x) * (q.y - p.y) - (pt.y - p.y) * (q.x - p.x) < 0;
    }

    public boolean isSubsegmentOf(Segment seg) {
        return abs((p.x - q.x) / (p.y - q.y) - (seg.p.x - seg.q.x) / (seg.p.y - seg.q.y)) < EPS
                && (p.x <= max(seg.p.x, seg.q.x))
                && (p.x >= min(seg.p.x, seg.q.x))
                && (p.y <= max(seg.p.y, seg.q.y))
                && (p.y >= min(seg.p.y, seg.q.y));
    }

    @Override
    public int compareTo(Segment o) {
        double x = max(min(p.x, q.x), min(o.p.x, o.q.x));
        if (abs(get_y(x) - o.get_y(x)) > EPS) return get_y(x) < o.get_y(x) ? -1 : 1;
        return id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment = (Segment) o;

        if (id != segment.id) return false;
        if (p != null ? !p.equals(segment.p) : segment.p != null) return false;
        return q != null ? q.equals(segment.q) : segment.q == null;

    }

    @Override
    public int hashCode() {
        int result = p != null ? p.hashCode() : 0;
        result = 31 * result + (q != null ? q.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "p=" + p +
                ", q=" + q +
                ", id=" + id +
                '}';
    }
}
