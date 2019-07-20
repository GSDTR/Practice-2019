import java.io.*;
import java.util.*;

class TuringMachine {
    private String alphabet = null;
    private int header;
    private int numberOfState = 1;
    private char symbol;
    private boolean stopped = false;
    private int numberOfSteps = 1000;
    private String inputTape = null;
    private List<String> inputStates = new ArrayList<>();
    private List<String> intermediateConfigurations = new ArrayList<>();

    void start(int initialPosition, String inputFileOfAlphabet, String inputFileOfStates, String inputFileOfTape){
        readStates(inputFileOfStates);
        if (!stopped) readInputTape(inputFileOfTape);
        if (!stopped) readAlphabet(inputFileOfAlphabet);
        if (!stopped) initialHeaderPosition(initialPosition);
        if (!stopped) parseString();
        printConfig();
        printResult();
    }

    private void stop (){
        stopped = true;
    }

    private void initialHeaderPosition(int initialPosition){
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

    void changeNumberOfSteps(int number) {
        numberOfSteps = number;
    }

    private void printResult(){
        try {
            File text = new File("output.txt");
            FileWriter writeText = new FileWriter(text);
            writeText.write(inputTape);
            System.out.println(inputTape);
            writeText.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printConfig(){
        try {
            File text = new File("intermediateConfigurations.txt");
            FileWriter writeText = new FileWriter(text);
            for (String intermediateConfiguration : intermediateConfigurations) {
                writeText.write(intermediateConfiguration);
                writeText.write("\n");
            }
            System.out.println(inputTape);
            writeText.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println("Лента: " + inputTape);
            readText.close();
            if (inputTape == null){
                inputTape = "Входной файл с лентой пуст";
                stop();
            }
            intermediateConfigurations.add("Входная лента:" + inputTape);
        }
        catch (FileNotFoundException e) {
            inputTape = "Входной файл с лентой не был найден";
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAlphabet(String alphabetFile) {
        try {
            int i = 0;
            File text = new File(alphabetFile);
            FileReader readText = new FileReader(text);
            BufferedReader readerOfText = new BufferedReader(readText);
            alphabet = readerOfText.readLine();
            readText.close();
            Set<Character> set = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            if (alphabet == null){
                inputTape = "Входной файл с алфавитом пуст";
                stop();
            }
            else {
                while (i <= alphabet.length() - 1){
                    if (alphabet.charAt(i) < '0' || alphabet.charAt(i) > '9') {
                        inputTape = "В алфавите должны быть только цифры";
                        stop();
                        break;
                    }
                    i++;
                }
                for (char c: alphabet.toCharArray())
                {
                    if (!set.contains(c))
                    {
                        set.add(c);
                        sb.append(c);
                    }
                }
                alphabet = sb.toString();
                intermediateConfigurations.add("Алфавит:" + alphabet);
                System.out.println("Алфавит = " + alphabet);
                alphabet += "_";
            }
        }
        catch (FileNotFoundException e) {
            inputTape = "Входной файл с алфавитом не был найден";
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseString() {
        String stringOfNumberOfState = "";
        String currentState = inputStates.get(numberOfState - 1);
        StringBuilder temp = new StringBuilder();
        intermediateConfigurations.add("Текущее состояние:" + numberOfState);
        int i = 0;
        int j = 0;
        boolean isReplacingAlphabetSymbol = false;
        boolean isFungibleAlphabetSymbol = false;
        try {
            while (i < currentState.length() && numberOfState != 0) {
                if (inputTape.charAt(header) != currentState.charAt(i)) {
                    while (currentState.charAt(i) != (' ')) {
                        i++;
                    }
                    i++;
                } else {
                    temp.append(currentState.charAt(i)).append(" заменяется на символ ");
                    while (j < alphabet.length()) {
                        if (currentState.charAt(i) == alphabet.charAt(j)) isFungibleAlphabetSymbol = true;
                        if (currentState.charAt(i + 2) == alphabet.charAt(j)) isReplacingAlphabetSymbol = true;
                        j++;
                    }
                    if (!isReplacingAlphabetSymbol || !isFungibleAlphabetSymbol) {
                        inputTape = "Найден неизвестный символ";
                        stop();
                        break;
                    }
                    i = i + 2;
                    temp.append(currentState.charAt(i));
                    if (currentState.charAt(i) >= '0' && currentState.charAt(i) <= '9'
                            || currentState.charAt(i) == '_') {
                        symbol = currentState.charAt(i);
                        i++;
                    }
                    if (inputTape.charAt(header) != symbol) {
                        changeSymbol(symbol);
                    }
                    if (currentState.charAt(i) == '>') {
                        moveRight();
                        temp.append(" и происходит сдвиг вправо");
                    }
                    if (currentState.charAt(i) == '<') {
                        moveLeft();
                        temp.append(" и происходит сдвиг влево");
                    }
                    i++;
                    while (currentState.charAt(i) >= '0' && currentState.charAt(i) <= '9') {
                        stringOfNumberOfState += currentState.charAt(i);
                        i++;
                        if (i >= currentState.length()) {
                            break;
                        }
                    }
                    if (numberOfState == Integer.parseInt(stringOfNumberOfState)) {
                        temp.append(", состояние не меняется");
                    }
                    else {
                        temp.append(", состояние меняется на ").append(Integer.parseInt(stringOfNumberOfState));
                    }
                    numberOfState = Integer.parseInt(stringOfNumberOfState);
                    if (i >= currentState.length() || currentState.charAt(i) == ' ') {
                        break;
                    }

                }
            }
            intermediateConfigurations.add(temp.toString());
            intermediateConfigurations.add("Лента  машины  принимает вид:" + inputTape);
            StringBuilder supportingTape = new StringBuilder(inputTape);
            supportingTape.setCharAt(header, '*');
            intermediateConfigurations.add("Текущий элемент  отмечен '*':" + supportingTape);
        }
        catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            inputTape = "Ошибка в файле с входными состояниями";
            stop();
        }
        numberOfSteps--;
        if (numberOfState == 0) {
            stop();
        }
        if (!stopped && numberOfSteps != 0) parseString();
    }
}