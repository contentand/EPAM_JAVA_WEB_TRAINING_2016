package com.daniilyurov.training.patterns.memento.tictactoe;

public class FieldMementoCaretaker {
    private FieldMemento memento;

    public FieldMemento getMemento() {
        return memento;
    }

    public void setMemento(FieldMemento memento) {
        this.memento = memento;
    }
}
