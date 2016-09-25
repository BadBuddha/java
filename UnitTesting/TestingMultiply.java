import static org.junit.Assert.assertEqulas;
import org.junit.Test;

public class MyTests{
    @Test
    public void multiplicationOfZeroIntegerShouldReturnZero(){
        Multiply tester = new Multiply(); // MyClass is tested

        assertsEquals("10 x 0 must be 0", 0, tester.doMultiply(10,0));
        assertsEquals("0 x 10 must be 0", 0, tester.doMultiply(0,10));
        assertsEquals("0 x 0 must be 0", 0, tester.doMultiply(0,0));
    }
}
