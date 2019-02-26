package pl.edu.pjatk.tau;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MathTest {
    MathLib mathLib;

    @Before()
    public void init() {
        mathLib = new MathLib();
    }

    @Test()
    public void testAdding() {
        Assert.assertEquals(4, mathLib.add(2, 2));
    }

    @Test()
    public void testSum() {
        Assert.assertEquals(1.0, mathLib.add(), 0.001);
    }
}
