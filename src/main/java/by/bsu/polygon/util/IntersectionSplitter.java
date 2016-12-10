package by.bsu.polygon.util;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author User
 * @date 08.11.2016
 */
public class IntersectionSplitter {
    public List<Polygon> split(List<Point> points, List<Segment> segments) {
        List<Polygon> list = new ArrayList<>();
        /**
         * Every segment is a part of one by.bsu.polygon.
         * Determines whether the segment was already used.
         */
        List<Boolean> used = new ArrayList<>(Arrays.asList(new Boolean[segments.size()]));
        /**
         * The direction of the by.bsu.polygon bypass changes every segment, unless the endpoint of the segment is not an intersection point.
         * Counts the direction of every segment bypass.
         */
        int[] directions = countDirections(segments);
        Collections.fill(used, false);
        while (used.contains(false)) {
            boolean polygonDirection = true;
            Polygon polygon = new Polygon();
            int i = findFirstUnused(used);
            /**
             * The direction of the current by.bsu.polygon bypass.
             * true == right, false == left.
             */
            boolean direction = (directions[i] & 1) == 0;
            List<Point> polygonPoints = new ArrayList<>();
            polygonPoints.add(points.get(i));
            Point curr = null;

            while (!polygonPoints.get(0).equals(curr)) {
                while (!(curr instanceof IntersectionPoint)) {
                    curr = points.get(polygonDirection ? i + 1 : i - 1);
                    used.set(polygonDirection ? i : i - 1, true);
                    // for polygons without self-intersections
                    /*
                    if (polygonPoints.get(0).equals(curr)) {
                        break m1;
                    }
                    */
                    polygonPoints.add(curr);
                    i += (polygonDirection ? 1 : -1);
                }
                if (polygonPoints.get(0).equals(curr)) {
                    break;
                }
                IntersectionPoint currIntersection = (IntersectionPoint) curr;
                Segment currentAlong = new Segment(polygonPoints.get(polygonPoints.size() - 2), curr);
                Segment newAlong = currentAlong.isSubsegmentOf(currIntersection.s1) ? currIntersection.s2 : currIntersection.s1;
                int j = direction ?
                        findRightSubsegment(newAlong, currentAlong, segments, currIntersection, used) :
                        findLeftSubsegment(newAlong, currentAlong, segments, currIntersection, used);
                Segment newSeg = segments.get(j);
                Point next = (newSeg.q.equals(currIntersection) ? newSeg.p : newSeg.q);
                polygonDirection = newSeg.p.equals(currIntersection);
                used.set(j, true);
                polygonPoints.add(next);
                curr = next;
                i = polygonDirection ? j + 1 : j;
            }

            polygon.setPoints(polygonPoints);
            list.add(polygon);
        }
        return list;
    }

    private int[] countDirections(List<Segment> segments) {
        int[] directions = new int[segments.size()];
        for (int i = 0; i < segments.size(); ++i) {
            if (i == 0) {
                directions[0] = 0;
            } else {
                directions[i] = directions[i - 1] + 1;
                if (!(segments.get(i - 1).q instanceof IntersectionPoint)) {
                    ++directions[i];
                }
            }
        }
        return directions;
    }

    private int findFirstUnused(List<Boolean> used) {
        for (int i = 0; i < used.size(); ++i) {
            if (!used.get(i)) {
                return i;
            }
        }
        return used.size();
    }

    private int findRightSubsegment(Segment s, Segment currAlong, List<Segment> segments, IntersectionPoint ipt, List<Boolean> used) {
        for (int i = 0; i < segments.size(); ++i) {
            Segment seg = segments.get(i);
            if (!used.get(i)
                    && seg.isSubsegmentOf(s)
                    && ((ipt.equals(seg.p) && currAlong.isRight(seg.q))
                    || (ipt.equals(seg.q)) && currAlong.isRight(seg.p))) {
                return i;
            }
        }
        return -1;
    }

    private int findLeftSubsegment(Segment s, Segment currAlong, List<Segment> segments, IntersectionPoint ipt, List<Boolean> used) {
        for (int i = 0; i < segments.size(); ++i) {
            Segment seg = segments.get(i);
            if (!used.get(i)
                    && seg.isSubsegmentOf(s)
                    && ((ipt.equals(seg.p) && currAlong.isLeft(seg.q))
                    || (ipt.equals(seg.q)) && currAlong.isLeft(seg.p))) {
                return i;
            }
        }
        return -1;
    }
}
