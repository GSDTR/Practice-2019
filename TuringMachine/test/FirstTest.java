import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FirstTest {
    @Test
    public void firstTest() {
        TuringMachine turing = new TuringMachine();
        turing.start("inputStates.txt", "inputTape.txt");
    }

    @Test
    public void secondTest() {
        TuringMachine turing = new TuringMachine();
        turing.start("inputStates2.txt", "inputTape2.txt");
    }
}
