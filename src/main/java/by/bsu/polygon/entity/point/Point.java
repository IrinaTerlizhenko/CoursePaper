package by.bsu.polygon.entity.point;

/**
 * @author User
 * @date 22.10.2016
 */
public class Point {
    public double x, y;

    /**
     * next point on fig1 or current figure
     */
    public Point next1;

    public long figureId;

    public Point() {
    }

    public Point(double x) {
        this.x = x;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, Point next1) {
        this.x = x;
        this.y = y;
        this.next1 = next1;
    }

    public Point(double x, double y, Point next1, long figureId) {
        this.x = x;
        this.y = y;
        this.next1 = next1;
        this.figureId = figureId;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
