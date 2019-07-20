import org.junit.Test;

public class Tests {

    // -1 - головка машины расположена на месте последнего элемента входной ленты

    @Test
    public void firstTest(){
        TuringMachine turing = new TuringMachine();
        turing.start(0, "alphabet.txt", "inputStates.txt", "inputTape.txt");
    }

    @Test
    public void secondTest() {
        TuringMachine turing = new TuringMachine();
        turing.start(-1, "alphabet2.txt", "inputStates2.txt", "inputTape2.txt");
    }

    @Test
    public void thirdTest() {
        TuringMachine turing = new TuringMachine();
        turing.changeNumberOfSteps(4000);
        turing.start(0, "alphabet3.txt", "inputStates3.txt", "inputTape3.txt");
    }

    @Test
    public void fourthTest() {
        TuringMachine turing = new TuringMachine();
        turing.changeNumberOfSteps(5);
        turing.start(0, "alphabet4.txt", "inputStates4.txt", "inputTape4.txt");
    }
    @Test
    public void fifthTest(){
        TuringMachine turing = new TuringMachine();
        turing.start(-3, "alphabet5.txt", "inputStates5.txt", "inputTape5.txt");
    }
}
