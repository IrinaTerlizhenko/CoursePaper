package by.bsu.polygon.func;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.Segment;
import by.bsu.polygon.entity.point.IntersectionPoint;
import by.bsu.polygon.entity.point.Point;

import java.util.*;

public class BinaryOperation {

    public List<Polygon> figureUnion(Polygon polygon1, Polygon polygon2) {
        List<Polygon> unionResult = new ArrayList<>();

        long id1 = polygon1.figureId;
        long id2 = polygon2.figureId;

        /*
        MEMORY: N points
         */
        List<Point> fig1 = polygon1.getPoints();
        List<Point> fig2 = polygon2.getPoints();

        /*
        TIME: O(n)
         */
        double area = AreaFinder.calculateArea(fig1);
        if (area > 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig1);
            recountNext(fig1);
        }
        /*
        TIME: O(n)
         */
        area = AreaFinder.calculateArea(fig2);
        if (area > 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig2);
            recountNext(fig2);
        }

        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg1 = SegmentManipulator.toSegmentList(fig1);
        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg2 = SegmentManipulator.toSegmentList(fig2);

        /*
        MEMORY: K intersections
         */
        HashMap<Point, TreeSet<IntersectionPoint>> intersections;
        HashMap<IntersectionPoint, Object> intersectionPoints = new HashMap<>();

        /*
        TIME: O(n^2)
         */
        intersections = Intersections.findIntersections(seg1, seg2, intersectionPoints);

        Set<Point> ins = intersections.keySet();
        for (Point pnt : ins) {
            TreeSet<IntersectionPoint> pointTreeSet = intersections.get(pnt);
            IntersectionPoint prevPnt = null;
            boolean first = true;
            if (pnt.figureId == id1) {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next1 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            } else {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next2 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            }
        }

        /*
        IF THERE ARE NO INTERSECTIONS, THAN IT CAN BE THREE CASES:
        a) if point of first polygon is inside the second polygon, than union is the second polygon;
        b) if point of second polygon is inside the first polygon, than union is the first polygon;
        c) otherwise, union is two polygons;

        TIME: O(n)
         */
        if (intersections.isEmpty()) {
            if (PointPositionChecker.checkIfInside(fig1.get(0), polygon2)) {
                // Фигура 1 в Фигуре 2 находится
                Collections.addAll(unionResult, new Polygon(fig2));
                return unionResult;
            } else if (PointPositionChecker.checkIfInside(fig2.get(0), polygon1)) {
                // Фигура 2 в фигуре 1 находится
                Collections.addAll(unionResult, new Polygon(fig1));
                return unionResult;
            } else {
                // Фигуры не вложены
                Collections.addAll(unionResult, new Polygon(fig1), new Polygon(fig2));
                return unionResult;
            }
        }

        // Нужные данные
        Set<IntersectionPoint> points;
        IntersectionPoint startPoint;
        Point currentPoint = null;

        // Одно множество объединения
        List<Point> union;

        // Текущая фигура
        FigureNumber currentFigure;

        // Пока не закончатся точки пересечения
        while (!intersectionPoints.isEmpty()) {
            // Обнуляем фигуру
            union = new ArrayList<>();
            // Берём одну из оставшихся точек пересечения
            points = intersectionPoints.keySet();
            startPoint = points.iterator().next();
            // Добавляем стартовую точку
            union.add(startPoint);
            intersectionPoints.remove(startPoint);
            // Вычисляем следующую точку
            currentFigure = findFigureNumberUnion(startPoint);
            if (currentFigure == FigureNumber.FIGURE_1) {
                currentPoint = startPoint.next1;
            } else {
                currentPoint = startPoint.next2;
            }
            // Пока мы не вернёмся в стартовую точку
            while (currentPoint != startPoint) {
                // Добавление точки к ответу
                union.add(currentPoint);
                // если это точка пересечения, то делать перескок на next другой фигуры
                if (currentPoint.getClass() == IntersectionPoint.class) {
                    IntersectionPoint intersect = (IntersectionPoint) currentPoint;
                    intersectionPoints.remove(currentPoint);
                    if (currentFigure == FigureNumber.FIGURE_1) {
                        currentFigure = FigureNumber.FIGURE_2;
                        currentPoint = intersect.next2;
                    } else {
                        currentFigure = FigureNumber.FIGURE_1;
                        currentPoint = intersect.next1;
                    }
                }
                // Если это не точка пересечения, то просто перескакиваем
                else {
                    currentPoint = currentPoint.next1;
                }
            }
            // добавление нового полигона в ответ
            unionResult.add(new Polygon(union));
        }
        return unionResult;
    }

    private void recountNext(List<Point> fig) {
        int size = fig.size();
        for (int i = 0; i < size - 1; i++) {
            Point point = fig.get(i);
            point.next1 = fig.get(i + 1);
        }
        Point point = fig.get(size - 1);
        point.next1 = fig.get(0);
    }

    private Segment findCurrentSegment(IntersectionPoint point) {
        Segment s1 = point.s1;
        Segment s2 = point.s2;
        SideOfPoint side = VectorOperations.checkSide(s2.p, s2.q, s1.q);
        if (side == SideOfPoint.RIGHT || side == SideOfPoint.ON_LINE) {
            return s2;
        } else if (side == SideOfPoint.LEFT) {
            return s1;
        }
        return s1;
    }

    public FigureNumber findFigureNumberUnion(IntersectionPoint point) {
        Segment s1 = point.s1;
        Segment s2 = point.s2;
        SideOfPoint side = VectorOperations.checkSide(s2.p, s2.q, s1.q);
        if (side == SideOfPoint.RIGHT || side == SideOfPoint.ON_LINE) {
            return FigureNumber.FIGURE_1;
        } else if (side == SideOfPoint.LEFT) {
            return FigureNumber.FIGURE_2;
        }
        return FigureNumber.FIGURE_1;
    }

    public List<Polygon> figureIntersection(Polygon polygon1, Polygon polygon2) {
        List<Polygon> unionResult = new ArrayList<>();

        long id1 = polygon1.figureId;
        long id2 = polygon2.figureId;

        /*
        MEMORY: N points
         */
        List<Point> fig1 = polygon1.getPoints();
        List<Point> fig2 = polygon2.getPoints();

        /*
        TIME: O(n)
         */
        double area = AreaFinder.calculateArea(fig1);
        if (area > 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig1);
            recountNext(fig1);
        }
        /*
        TIME: O(n)
         */
        area = AreaFinder.calculateArea(fig2);
        if (area > 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig2);
            recountNext(fig2);
        }

        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg1 = SegmentManipulator.toSegmentList(fig1);
        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg2 = SegmentManipulator.toSegmentList(fig2);

        /*
        MEMORY: K intersections
         */
        HashMap<Point, TreeSet<IntersectionPoint>> intersections;
        HashMap<IntersectionPoint, Object> intersectionPoints = new HashMap<>();

        /*
        TIME: O(n^2)
         */
        intersections = Intersections.findIntersections(seg1, seg2, intersectionPoints);

        Set<Point> ins = intersections.keySet();
        for (Point pnt : ins) {
            TreeSet<IntersectionPoint> pointTreeSet = intersections.get(pnt);
            IntersectionPoint prevPnt = null;
            boolean first = true;
            if (pnt.figureId == id1) {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next1 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            } else {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next2 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            }

        }
        /*
        IF THERE ARE NO INTERSECTIONS, THAN IT CAN BE THREE CASES:
        a) if point of first polygon is inside the second polygon, than union is the second polygon;
        b) if point of second polygon is inside the first polygon, than union is the first polygon;
        c) otherwise, union is two polygons;

        TIME: O(n)
         */
        if (intersections.isEmpty()) {
            if (PointPositionChecker.checkIfInside(fig1.get(0), polygon2)) {
                // Фигура 1 в Фигуре 2 находится
                Collections.addAll(unionResult, new Polygon(fig1));
                return unionResult;
            } else if (PointPositionChecker.checkIfInside(fig2.get(0), polygon1)) {
                // Фигура 2 в фигуре 1 находится
                Collections.addAll(unionResult, new Polygon(fig2));
                return unionResult;
            } else {
                // Фигуры не вложены
                return unionResult;
            }
        }

        // Нужные данные
        Set<IntersectionPoint> points;
        IntersectionPoint startPoint;
        Point currentPoint = null;

        // Одно множество объединения
        List<Point> union;

        // Текущая фигура
        FigureNumber currentFigure;

        // Пока не закончатся точки пересечения
        while (!intersectionPoints.isEmpty()) {
            // Обнуляем фигуру
            union = new ArrayList<>();
            // Берём одну из оставшихся точек пересечения
            points = intersectionPoints.keySet();
            startPoint = points.iterator().next();
            // Добавляем стартовую точку
            union.add(startPoint);
            intersectionPoints.remove(startPoint);
            // Вычисляем следующую точку
            currentFigure = findFigureNumberIntersection(startPoint);
            if (currentFigure == FigureNumber.FIGURE_1) {
                currentPoint = startPoint.next1;
            } else {
                currentPoint = startPoint.next2;
            }
            // Пока мы не вернёмся в стартовую точку
            while (currentPoint != startPoint) {
                // Добавление точки к ответу
                union.add(currentPoint);
                // если это точка пересечения, то делать перескок на next другой фигуры
                if (currentPoint.getClass() == IntersectionPoint.class) {
                    IntersectionPoint intersect = (IntersectionPoint) currentPoint;
                    intersectionPoints.remove(currentPoint);
                    if (currentFigure == FigureNumber.FIGURE_1) {
                        currentFigure = FigureNumber.FIGURE_2;
                        currentPoint = intersect.next2;
                    } else {
                        currentFigure = FigureNumber.FIGURE_1;
                        currentPoint = intersect.next1;
                    }
                }
                // Если это не точка пересечения, то просто перескакиваем
                else {
                    currentPoint = currentPoint.next1;
                }
            }
            // добавление нового полигона в ответ
            unionResult.add(new Polygon(union));
        }
        return unionResult;
    }

    private FigureNumber findFigureNumberIntersection(IntersectionPoint point) {
        Segment s1 = point.s1;
        Segment s2 = point.s2;
        SideOfPoint side = VectorOperations.checkSide(s2.p, s2.q, s1.q);
        if (side == SideOfPoint.RIGHT || side == SideOfPoint.ON_LINE) {
            return FigureNumber.FIGURE_2;
        } else if (side == SideOfPoint.LEFT) {
            return FigureNumber.FIGURE_1;
        }
        return FigureNumber.FIGURE_2;
    }

    public List<Polygon> figureDifference(Polygon polygon1, Polygon polygon2) {
        List<Polygon> unionResult = new ArrayList<>();

        long id1 = polygon1.figureId;
        long id2 = polygon2.figureId;

        /*
        MEMORY: N points
         */
        List<Point> fig1 = polygon1.getPoints();
        List<Point> fig2 = polygon2.getPoints();

        /*
        TIME: O(n)
         */
        double area = AreaFinder.calculateArea(fig1);
        if (area > 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig1);
            recountNext(fig1);
        }
        /*
        TIME: O(n)
         */
        area = AreaFinder.calculateArea(fig2);
        if (area < 0) {
            /*
            TIME: O(n)
             */
            Collections.reverse(fig2);
            recountNext(fig2);
        }

        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg1 = SegmentManipulator.toSegmentList(fig1);
        /*
        MEMORY: N segments
        TIME: O(n)
         */
        List<Segment> seg2 = SegmentManipulator.toSegmentList(fig2);

        /*
        MEMORY: K intersections
         */
        HashMap<Point, TreeSet<IntersectionPoint>> intersections;
        HashMap<IntersectionPoint, Object> intersectionPoints = new HashMap<>();

        /*
        TIME: O(n^2)
         */
        intersections = Intersections.findIntersections(seg1, seg2, intersectionPoints);

        Set<Point> ins = intersections.keySet();
        for (Point pnt : ins) {
            TreeSet<IntersectionPoint> pointTreeSet = intersections.get(pnt);
            IntersectionPoint prevPnt = null;
            boolean first = true;
            if (pnt.figureId == id1) {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next1 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            } else {
                for (IntersectionPoint insPnt : pointTreeSet) {
                    if (first) {
                        pnt.next1 = insPnt;
                        prevPnt = insPnt;
                        first = false;
                    } else {
                        prevPnt.next2 = insPnt;
                        prevPnt = insPnt;
                    }
                }
            }

        }
        /*
        IF THERE ARE NO INTERSECTIONS, THAN IT CAN BE THREE CASES:
        a) if point of first polygon is inside the second polygon, than union is the second polygon;
        b) if point of second polygon is inside the first polygon, than union is the first polygon;
        c) otherwise, union is two polygons;

        TIME: O(n)
         */
        if (intersections.isEmpty()) {
            if (PointPositionChecker.checkIfInside(fig1.get(0), polygon2)) {
                // Фигура 1 в Фигуре 2 находится
                return unionResult;
            } else if (PointPositionChecker.checkIfInside(fig2.get(0), polygon1)) {
                // Фигура 2 в фигуре 1 находится
                Collections.addAll(unionResult, new Polygon(fig1), new Polygon(fig2));
                return unionResult;
            } else {
                // Фигуры не вложены
                Collections.addAll(unionResult, new Polygon(fig1));
                return unionResult;
            }
        }

        // Нужные данные
        Set<IntersectionPoint> points;
        IntersectionPoint startPoint;
        Point currentPoint = null;

        // Одно множество объединения
        List<Point> union;

        // Текущая фигура
        FigureNumber currentFigure;

        // Пока не закончатся точки пересечения
        while (!intersectionPoints.isEmpty()) {
            // Обнуляем фигуру
            union = new ArrayList<>();
            // Берём одну из оставшихся точек пересечения
            points = intersectionPoints.keySet();
            startPoint = points.iterator().next();
            // Добавляем стартовую точку
            union.add(startPoint);
            intersectionPoints.remove(startPoint);
            // Вычисляем следующую точку
            currentFigure = findFigureNumberIntersection(startPoint);
            if (currentFigure == FigureNumber.FIGURE_1) {
                currentPoint = startPoint.next1;
            } else {
                currentPoint = startPoint.next2;
            }
            // Пока мы не вернёмся в стартовую точку
            while (currentPoint != startPoint) {
                // Добавление точки к ответу
                union.add(currentPoint);
                // если это точка пересечения, то делать перескок на next другой фигуры
                if (currentPoint.getClass() == IntersectionPoint.class) {
                    IntersectionPoint intersect = (IntersectionPoint) currentPoint;
                    intersectionPoints.remove(currentPoint);
                    if (currentFigure == FigureNumber.FIGURE_1) {
                        currentFigure = FigureNumber.FIGURE_2;
                        currentPoint = intersect.next2;
                    } else {
                        currentFigure = FigureNumber.FIGURE_1;
                        currentPoint = intersect.next1;
                    }
                }
                // Если это не точка пересечения, то просто перескакиваем
                else {
                    currentPoint = currentPoint.next1;
                }
            }
            // добавление нового полигона в ответ
            unionResult.add(new Polygon(union));
        }
        return unionResult;
    }

}
