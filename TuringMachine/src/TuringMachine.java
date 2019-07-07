import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TuringMachine {
    int header;
    int alphabetSize;
    int numberOfState = 1;
    char symbol;
    boolean stopped = false;
    int n = 0;
    String alphabet, output, inputTape;
    List<String> inputStates = new ArrayList<>();

    public void start(boolean initialPosition, String inputFileOfStates, String inputFileOfTape){
        readStates(inputFileOfStates);
        readInputTape(inputFileOfTape);
        endOrBeginning(initialPosition);
        parseString();
        printResult();
    }

    void stop (){
        stopped = true;
    }

    void endOrBeginning(boolean initialPosition){
        if (initialPosition != true){
            header = inputTape.length() - 1;
        }
        else {
            header = 0;
        }
    }

    void printResult(){
        System.out.println(inputTape); // вывод в текстовый файл пока не стал делать
    }

    void moveLeft() {
        header--;
        if (header < 0) {
            StringBuffer sb = new StringBuffer(inputTape);
            sb.insert(0, "_");
            inputTape = sb.toString();
            header = 0;
        }
    }
    void moveRight() {
        header++;
        if (header == inputTape.length()) {
            inputTape += '_';
        }
    }
    void changeSymbol(char sym) {
        StringBuilder temp = new StringBuilder(inputTape);
        temp.setCharAt(header, sym);
        inputTape = temp.toString();
    }

    public void readStates(String inputFile) {
        try {
            File text = new File(inputFile);
            FileReader readText = new FileReader(text);
            BufferedReader readerOfText = new BufferedReader(readText);
            String line = readerOfText.readLine();
            inputStates.add(line);
            while (line != null) {
                line = readerOfText.readLine();
                inputStates.add(line);
            }
            inputStates.remove(inputStates.size() - 1);
            System.out.println(inputStates);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInputTape(String inputFile) {
        try {
            File text = new File(inputFile);
            FileReader readText = new FileReader(text);
            BufferedReader readerOfText = new BufferedReader(readText);
            String line = readerOfText.readLine();
            inputTape = line;
            System.out.println("input = " + inputTape);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseString(){
        String str = "";
        String temp = inputStates.get(numberOfState - 1);
        int i = 0;
        while (i < temp.length()){
            if (inputTape.charAt(header) != temp.charAt(i)) {
                while (temp.charAt(i) != (' ')) {
                    i++;
                }
                i++;
            }
            else {
                i = i + 2;
                if (temp.charAt(i) >= '0' && temp.charAt(i) <= '9' || temp.charAt(i) == '_') {
                    symbol = temp.charAt(i);
                    i++;
                }
                if (inputTape.charAt(header) != symbol){
                    changeSymbol(symbol);
                }
                if (temp.charAt(i) == '>') {
                    moveRight();
                }
                if (temp.charAt(i) == '<') {
                    moveLeft();
                }
                i++;
                while(temp.charAt(i) >= '0' && temp.charAt(i) <= '9'){
                    str += temp.charAt(i);
                    i++;
                    if (i >= temp.length()){
                        break;
                    }
                }
                numberOfState = Integer.parseInt(str);
                if (i >= temp.length() || temp.charAt(i) == ' ') {
                    break;
                }
            }
        }
        n++;
        if (numberOfState == 0) {
            stop();
        }
        if (stopped != true && n != 1000) parseString();
    }
}
