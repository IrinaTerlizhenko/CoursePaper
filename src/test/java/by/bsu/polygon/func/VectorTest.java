package by.bsu.polygon.func;

import by.bsu.polygon.entity.point.Point;
import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

    @Test
    public void positiveTest(){
        double result = VectorOperations.crossProduct(new Point(0, 0), new Point(1, 0), new Point(0, 1));
        Assert.assertTrue(result > 0);
    }

    @Test
    public void negativeTest(){
        double result = VectorOperations.crossProduct(new Point(0, 0), new Point(0, 1), new Point(1, 0));
        Assert.assertTrue(result < 0);
    }

    @Test
    public void zeroTest(){
        double result = VectorOperations.crossProduct(new Point(0, 0), new Point(1, 1), new Point(2, 2));
        Assert.assertTrue(result == 0);
    }
}
