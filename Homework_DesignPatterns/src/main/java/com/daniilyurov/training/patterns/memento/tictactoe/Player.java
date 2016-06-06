package com.daniilyurov.training.patterns.memento.tictactoe;

public abstract class Player {
    protected Cell mark;
    protected String name;
    protected Player opponent;
    protected Field field;
    protected FieldMementoCaretaker save;

    public Player(Cell mark) {
        this.field = Field.getInstance();
        this.mark = mark;
        this.save = new FieldMementoCaretaker();
    }

    public void setOpponent(Player opponent) {
        if (opponent.mark == this.mark) {
            throw new IllegalStateException("Players should have different marks!");
        }
        this.opponent = opponent;
    }

    public State takeTurn() {
        State result = State.INIT;
        do {

            result = turnTakingImplementation(result == State.OCCUPIED ? "This cell is already occupied!" : "");
        }
        while (result == State.OCCUPIED);
        return result;
    }

    protected void takeOneStepBack() {
        field.restore(save.getMemento());
    }

    public void save() {
        save.setMemento(field.save());
    }

    public abstract void setName();
    protected abstract State turnTakingImplementation(String message);

}
