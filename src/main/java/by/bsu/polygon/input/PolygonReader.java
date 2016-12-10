package by.bsu.polygon.input;

import by.bsu.polygon.entity.Polygon;
import by.bsu.polygon.entity.point.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author User
 * @date 28.10.2016
 */
public class PolygonReader {
    private BufferedReader reader;

    public PolygonReader() {
    }

    public PolygonReader(String file) {
        setFile(file);
    }

    public void setFile(String file) {
        InputStream resourceAsStream = getClass().getResourceAsStream(file);
        reader = new BufferedReader(new InputStreamReader(resourceAsStream));
    }

    public Polygon read() throws IOException {
        ArrayList<Point> points = new ArrayList<>();
        String line = reader.readLine();
        while (!line.isEmpty() && !"S".equals(line) && !"s".equals(line)) {
            String[] split = line.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            points.add(new Point(x, y));
            line = reader.readLine();
        }
        if ("S".equals(line)) {
            points.remove(points.size() - 1);
        }
        return new Polygon(points);
    }
}
