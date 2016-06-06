package com.daniilyurov.training.patterns.memento.tictactoe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Field {
    private Cell [][] cells; // 0 - for O and 1 - for X
    private static volatile Field instance; // Singleton.

    // killing public constructor for implementing Singleton
    private Field() {
        cells = new Cell[3][3];
        Arrays.fill(cells[0], Cell.EMPTY);
        Arrays.fill(cells[1], Cell.EMPTY);
        Arrays.fill(cells[2], Cell.EMPTY);
    }

    public static Field getInstance() {
        if (instance == null) {
            synchronized (Field.class) {
                if (instance == null) {
                    instance = new Field();
                }
            }
        }
        return instance;
    }

    public State occupy(int cellIndex1, int cellIndex2, Cell mark) {
        if (cells[cellIndex1][cellIndex2] != Cell.EMPTY) {
            return State.OCCUPIED;
        }
        this.cells[cellIndex1][cellIndex2] = mark;
        return fieldState();
    }

    private State fieldState() {

        Cell leftCross = cells[0][0];
        Cell rightCross = cells[0][2];
        boolean leftCrossOccupiedCompletely = true;
        boolean rightCrossOccupiedCompletely = true;

        for (int i = 0; i < 3; i++) {

            if (leftCross != cells[i][i]) {
                leftCrossOccupiedCompletely = false;
            }

            if (rightCross != cells[i][2 - i]) {
                rightCrossOccupiedCompletely = false;
            }

            Cell vertical = cells[0][i];
            Cell horizontal = cells[i][0];
            if (vertical == Cell.EMPTY || horizontal == Cell.EMPTY) {
                continue;
            }

            boolean verticalOccupiedCompletely = true;
            boolean horizontalOccupiedCompletely = true;
            for (int j = 1; j < 3; j++) {
                if (vertical != cells[j][i]) {
                    verticalOccupiedCompletely = false;
                }
                if (horizontal != cells[i][j]) {
                    horizontalOccupiedCompletely = false;
                }
            }

            if (verticalOccupiedCompletely || horizontalOccupiedCompletely) {
                return State.WIN;
            }
        }

        if (leftCrossOccupiedCompletely && (leftCross != Cell.EMPTY)) {
            return State.WIN;
        }
        if (rightCrossOccupiedCompletely && (rightCross != Cell.EMPTY)) {
            return State.WIN;
        }

        return State.CONTINUE;
    }

    public FieldMemento save() {
        return new FieldMemento(cells);
    }

    public void restore(FieldMemento memento) {
        this.cells = memento.getCells();
    }

    public Cell getCallAt(int cellIndex1, int cellIndex2) {
        return cells[cellIndex1][cellIndex2];
    }
}

