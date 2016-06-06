package com.daniilyurov.training.patterns.memento.tictactoe;

public enum Cell {
    O("O"),
    X("X"),
    EMPTY(" ");

    public String value;
    private Cell(String value) {
        this.value = value;
    }
}
