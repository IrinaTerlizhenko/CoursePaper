package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.lang.Math.*;

/**
 * @author Irina Terlizhenko
 * @date 20.10.2016
 */
public class SweepLine {
    private static final double EPS = 1E-9;

    public static List<IntPair> solve(Polygon polygon) {
        List<IntPair> v = new ArrayList<>();

        List<Segment> seg = polygon.toSegmentList();

        /* arrange events in the order sweep line encounters them */
        TreeSet<Event> events = new TreeSet<>();

        /* populate events with all edges start and end */
        for (int i = 0; i < polygon.length(); ++i) {
            /* sweep line encounters new edge */
            events.add(new Event(min(seg.get(i).p.x, seg.get(i).q.x), +1, i));
            /* sweep line encounters end of the edge */
            events.add(new Event(max(seg.get(i).p.x, seg.get(i).q.x), -1, i));
        }

        TreeSet<Segment> set = new TreeSet<>();

        while (!events.isEmpty()) {
            Event e = events.first();
            int id = e.id;
            if (e.tp == +1) {
                Segment nxt = set.ceiling(seg.get(id)),
                        prv = nxt != null ? set.lower(nxt) : set.floor(seg.get(id));
                if (nxt != null && nxt.id != seg.get(id).id && !hasEqualEndPoints(nxt, seg.get(id)) && intersect(nxt, seg.get(id))) {
                    v.add(new IntPair(nxt.id, seg.get(id).id));
                    double x_intersect = Intersection.xIntersectionPoint(nxt, seg.get(id));
                    Event evt = new Event(x_intersect, 0, 0);
                    evt.id1 = nxt.id;
                    evt.id2 = seg.get(id).id;
                    events.add(evt);
                }
                if (prv != null && prv.id != seg.get(id).id && !hasEqualEndPoints(prv, seg.get(id)) && intersect(prv, seg.get(id))) {
                    v.add(new IntPair(prv.id, seg.get(id).id));
                    double x_intersect = Intersection.xIntersectionPoint(prv, seg.get(id));
                    Event evt = new Event(x_intersect, 0, 0);
                    evt.id1 = seg.get(id).id;
                    evt.id2 = prv.id;
                    events.add(evt);
                }
                set.add(seg.get(id));
            } else if (e.tp == -1) {
                Segment nxt = set.higher(seg.get(id)),
                        prv = set.lower(seg.get(id));
                if (nxt != null && prv != null && nxt.id != prv.id && !hasEqualEndPoints(nxt, prv) && intersect(nxt, prv)) {
                    v.add(new IntPair(prv.id, nxt.id));
                    double x_intersect = Intersection.xIntersectionPoint(prv, nxt);
                    Event evt = new Event(x_intersect, 0, 0);
                    evt.id1 = nxt.id;
                    evt.id2 = prv.id;
                    events.add(evt);
                }
                set.remove(seg.get(id));
            } else {
                int id1 = e.id1,
                        id2 = e.id2;
                Segment nxt = set.higher(seg.get(id1)),
                        prv = set.lower(seg.get(id2));
                if (nxt != null && nxt.id != seg.get(id2).id && !hasEqualEndPoints(nxt, seg.get(id2)) && intersect(nxt, seg.get(id2))) {
                    v.add(new IntPair(nxt.id, seg.get(id2).id));
                    double x_intersect = Intersection.xIntersectionPoint(nxt, seg.get(id2));
                    Event evt = new Event(x_intersect, 0, 0);
                    evt.id1 = nxt.id;
                    evt.id2 = seg.get(id2).id;
                    events.add(evt);
                }
                if (prv != null && prv.id != seg.get(id1).id && !hasEqualEndPoints(prv, seg.get(id1)) && intersect(prv, seg.get(id1))) {
                    v.add(new IntPair(prv.id, seg.get(id1).id));
                    double x_intersect = Intersection.xIntersectionPoint(prv, seg.get(id1));
                    Event evt = new Event(x_intersect, 0, 0);
                    evt.id1 = prv.id;
                    evt.id2 = seg.get(id1).id;
                    events.add(evt);
                }

                Segment s1 = seg.get(id1),
                        s2 = seg.get(id2);

                double x_intersect = Intersection.xIntersectionPoint(s1, s2);
                Point p1 = new Point();

                double EPS1 = 1E-5;

                p1.y = s1.get_y(x_intersect + EPS1);
                p1.x = x_intersect + EPS1;

                s1.p = p1;

                set.remove(seg.get(id1));
                set.add(s1);
            }
            events.remove(e);
        }
        return v;
    }

    private static boolean hasEqual(Point p1, Point p2, Point p3, Point p4) {
        return (p1.equals(p3) || p1.equals(p4) || p2.equals(p3) || p2.equals(p4));
    }

    private static boolean hasEqualEndPoints(Segment s1, Segment s2) {
        return hasEqual(s1.p, s1.q, s2.p, s2.q);
    }

    private static boolean intersect(Segment a, Segment b) {
        return intersect1d(a.p.x, a.q.x, b.p.x, b.q.x)
                && intersect1d(a.p.y, a.q.y, b.p.y, b.q.y)
                && vec(a.p, a.q, b.p) * vec(a.p, a.q, b.q) <= 0
                && vec(b.p, b.q, a.p) * vec(b.p, b.q, a.q) <= 0;
    }

    private static boolean intersect1d(double l1, double r1, double l2, double r2) {
        if (l1 > r1) {
            double tmp = l1;
            l1 = r1;
            r1 = tmp;
        }
        if (l2 > r2) {
            double tmp = l2;
            l2 = r2;
            r2 = tmp;
        }
        return max(l1, l2) <= min(r1, r2) + EPS;
    }

    private static int vec(Point a, Point b, Point c) {
        double s = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return (abs(s) < EPS) ? 0 : ((s > 0) ? 1 : -1);
    }

    /**
     * Class that implements any kind of event sweep line can come into.
     */
    private static class Event implements Comparable<Event> {
        /**
         * x-coordinate of the point where event happens
         */
        double x;
        /**
         * type of the event: 1 = segment start, -1 = segment end; 0 = segments intersect
         */
        int tp;
        /**
         * id of the event
         */
        int id;
        /** ids of the segments that intersect */
        /**
         * id of the upper segment to the left
         */
        int id1;
        /**
         * id of the lower segment to the left
         */
        int id2;

        public Event() {
        }

        public Event(double x, int tp, int id) {
            this.x = x;
            this.tp = tp;
            this.id = id;
        }

        @Override
        public int compareTo(Event o) {
            if (abs(x - o.x) > EPS) return x < o.x ? -1 : 1;
            if (abs(tp - o.tp) > EPS) return tp > o.tp ? -1 : 1;
            return id - o.id;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "x=" + x +
                    ", tp=" + tp +
                    ", id=" + id +
                    ", id1=" + id1 +
                    ", id2=" + id2 +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Event event = (Event) o;

            if (Double.compare(event.x, x) != 0) return false;
            if (tp != event.tp) return false;
            if (id != event.id) return false;
            if (id1 != event.id1) return false;
            return id2 == event.id2;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(x);
            result = (int) (temp ^ (temp >>> 32));
            result = 31 * result + tp;
            result = 31 * result + id;
            result = 31 * result + id1;
            result = 31 * result + id2;
            return result;
        }
    }
}