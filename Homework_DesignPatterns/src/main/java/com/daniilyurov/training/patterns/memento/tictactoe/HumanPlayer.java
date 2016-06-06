package com.daniilyurov.training.patterns.memento.tictactoe;

import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HumanPlayer extends Player {

    private Scanner scanner;

    public HumanPlayer(Cell mark) {
        super(mark);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void setName() {
        System.out.println("What's your name?");
        name = scanner.nextLine();
    }

    @Override
    protected State turnTakingImplementation(String message) {

        Stream.generate(() -> "\n").limit(20).forEach(System.out::println);
        System.out.println(message);
        drawField();
        System.out.println("Take turn! (enter R to go one step back, enter Q to quit.");
        String response = scanner.nextLine();
        switch (response) {
            case "R":
                takeOneStepBack();
                return turnTakingImplementation("REVERTED BACK!");
            case "Q":
                return State.QUIT;
            default:
                if (response.length() == 2 && Character.isDigit(response.charAt(0))
                        && Character.isDigit(response.charAt(1))) {

                    int cellIndex1 = Integer.valueOf(response.substring(0,1));
                    int cellIndex2 = Integer.valueOf(response.substring(1,2));

                    if (cellIndex1 < 3 && cellIndex2 < 3) {
                        return field.occupy(cellIndex1, cellIndex2, mark);
                    }
                }
                return turnTakingImplementation("Invalid input! Q, R, and 2 digit combinations of 0,1,2 allowed!");
        }

    }

    private void drawField() {
        System.out.println(" |0|1|2|");
        for (int i = 0; i < 3; i++) {
            System.out.println(i + "|" +
                    field.getCallAt(i, 0).value + "|" +
                    field.getCallAt(i, 1).value +"|" +
                    field.getCallAt(i, 2).value + "|");
        }
    }
}
