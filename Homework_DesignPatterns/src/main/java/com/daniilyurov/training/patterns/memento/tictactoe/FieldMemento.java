package com.daniilyurov.training.patterns.memento.tictactoe;

import java.util.Arrays;
import java.util.Set;

public class FieldMemento {
    private Cell [][] cells;

    public FieldMemento(Cell[][] cells) {
        Cell[][] copy = new Cell[3][3];
        copy[0] = Arrays.copyOf(cells[0], cells.length);
        copy[1] = Arrays.copyOf(cells[1], cells.length);
        copy[2] = Arrays.copyOf(cells[2], cells.length);
        this.cells = copy;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
