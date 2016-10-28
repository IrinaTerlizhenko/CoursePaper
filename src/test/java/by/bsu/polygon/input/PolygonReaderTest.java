package by.bsu.polygon.input;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 * @date 28.10.2016
 */
public class PolygonReaderTest {
    @Test
    public void read() throws IOException {
        PolygonReader reader = new PolygonReader("/input.txt");
        Polygon polygon = reader.read();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,2));
        points.add(new Point(2,1));
        Polygon expected = new Polygon(points);
        Assert.assertEquals(expected, polygon);
    }
}
