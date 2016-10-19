package by.bsu.polygon.entity;

/**
 * Created by User on 19.10.2016.
 */
public class Point2D<T> {
    private T x;
    private T y;

    public Point2D() {
    }

    public Point2D(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D<?> point2D = (Point2D<?>) o;

        if (x != null ? !x.equals(point2D.x) : point2D.x != null) return false;
        return y != null ? y.equals(point2D.y) : point2D.y == null;

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    public static void main(String[] args) {

    }
}
