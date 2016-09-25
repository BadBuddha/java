package helloworld;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class HelloTest{
    private Greeter greet = new Greeter();

    @Test
    public void helloerSaysHello(){
        assertThat(greet.SayHello(), containsString("cruel"));
    }
}
