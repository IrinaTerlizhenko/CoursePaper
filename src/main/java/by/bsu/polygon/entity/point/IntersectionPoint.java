package by.bsu.polygon.entity.point;

import by.bsu.polygon.entity.Segment;

/**
 * @author User
 * @date 28.10.2016
 */
public class IntersectionPoint extends Point {
    public Segment s1;
    public Segment s2;

    public IntersectionPoint() {
    }

    public IntersectionPoint(double x, Segment s1, Segment s2) {
        super(x);
        this.s1 = s1;
        this.s2 = s2;
    }

    public IntersectionPoint(double x, double y, Segment s1, Segment s2) {
        super(x, y);
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        IntersectionPoint that = (IntersectionPoint) o;

        if (s1 != null ? !(s1.equals(that.s1) /*|| s1.equals(that.s2)*/) : that.s1 != null) return false;
        return s2 != null ? (s2.equals(that.s2) /*|| s2.equals(that.s1)*/) : that.s2 == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (s1 != null ? s1.hashCode() : 0);
        result = 31 * result + (s2 != null ? s2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IntersectionPoint{" +
                "x=" + x +
                ", y=" + y +
                ", s1=" + s1 +
                ", s2=" + s2 +
                '}';
    }
}
