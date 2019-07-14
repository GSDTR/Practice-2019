import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TuringMachine {
    private int header;
    private int numberOfState = 1;
    private char symbol;
    private boolean stopped = false;
    private int n = 0;
    private String inputTape = null;
    private List<String> inputStates = new ArrayList<>();

    void start(int initialPosition, String inputFileOfStates, String inputFileOfTape){
        readStates(inputFileOfStates);
        readInputTape(inputFileOfTape);
        initialHeaderPosition(initialPosition);
        if (!stopped) {
            parseString();
        }
        printResult();
    }

    private void stop (){
        stopped = true;
    }

    private void initialHeaderPosition(int initialPosition){
        if (!stopped) {
            if (initialPosition == -1) {
                header = inputTape.length() - 1;
            } else {
                if (initialPosition >= inputTape.length() || initialPosition < 0) {
                    inputTape = "Неверно указано начальное положение головки машины";
                    stop();
                } else {
                    header = initialPosition;
                }
            }
        }
    }

    private void printResult(){
        try {
            String outputFile = "output.txt";
            File text = new File(outputFile);
            FileWriter writeText = new FileWriter(text);
            writeText.write(inputTape);
            System.out.println(inputTape);
            writeText.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(inputTape);
    }

    private void moveLeft() {
        header--;
        if (header < 0) {
            StringBuilder sb = new StringBuilder(inputTape);
            sb.insert(0, "_");
            inputTape = sb.toString();
            header = 0;
        }
    }
    private void moveRight() {
        header++;
        if (header == inputTape.length()) {
            inputTape += '_';
        }
    }
    private void changeSymbol(char sym) {
        StringBuilder temp = new StringBuilder(inputTape);
        temp.setCharAt(header, sym);
        inputTape = temp.toString();
    }

    private void readStates(String inputFile) {
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
            readText.close();
            if (inputStates.isEmpty()) {
                inputTape = "Входной файл с состояниями пуст";
                stop();
            }
        }
        catch (FileNotFoundException e) {
            inputTape = "Входной файл с состояниями не был найден";
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInputTape(String inputFile) {
        try {
            File text = new File(inputFile);
            FileReader readText = new FileReader(text);
            BufferedReader readerOfText = new BufferedReader(readText);
            inputTape = readerOfText.readLine();
            System.out.println("input = " + inputTape);
            readText.close();
            if (inputTape == null){
                inputTape = "Входной файл с лентой пуст";
                stop();
            }
        }
        catch (FileNotFoundException e) {
            inputTape = "Входной файл с лентой не был найден";
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseString() {
        String str = "";
        String temp = inputStates.get(numberOfState - 1);
        int i = 0;
        try {
            while (i < temp.length() && numberOfState != 0) {
                if (inputTape.charAt(header) != temp.charAt(i)) {
                    while (temp.charAt(i) != (' ')) {
                        i++;
                    }
                    i++;
                } else {
                    i = i + 2;
                    if (temp.charAt(i) >= '0' && temp.charAt(i) <= '9' || temp.charAt(i) == '_') {
                        symbol = temp.charAt(i);
                        i++;
                    }
                    if (inputTape.charAt(header) != symbol) {
                        changeSymbol(symbol);
                    }
                    if (temp.charAt(i) == '>') {
                        moveRight();
                    }
                    if (temp.charAt(i) == '<') {
                        moveLeft();
                    }
                    i++;
                    while (temp.charAt(i) >= '0' && temp.charAt(i) <= '9') {
                        str += temp.charAt(i);
                        i++;
                        if (i >= temp.length()) {
                            break;
                        }
                    }
                    numberOfState = Integer.parseInt(str);
                    if (i >= temp.length() || temp.charAt(i) == ' ') {
                        break;
                    }
                }
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            inputTape = "Ошибка в файле с входными состояниями";
            stop();
        }
        n++;
        if (numberOfState == 0) {
            stop();
        }
        if (!stopped && n != 1000) parseString();
    }
}