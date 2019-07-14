import org.junit.Test;

public class Tests {
    // -1 - головка машины расположена на месте последнего элемента входной ленты
    @Test
    public void firstTest() {
        TuringMachine turing = new TuringMachine();
        turing.start(0, "inputStates.txt", "inputTape.txt");
    }

    @Test
    public void secondTest() {
        TuringMachine turing = new TuringMachine();
        turing.start(-2, "inputStates2.txt", "inputTape2.txt");
    }

    @Test
    public void thirdTest() {
        TuringMachine turing = new TuringMachine();
        turing.start(0, "inputStates3.txt", "inputTape3.txt");
    }

    @Test
    public void fourthTest() {
        TuringMachine turing = new TuringMachine();
        turing.start(0, "inputStates4.txt", "inputTape4.txt");
    }
}
