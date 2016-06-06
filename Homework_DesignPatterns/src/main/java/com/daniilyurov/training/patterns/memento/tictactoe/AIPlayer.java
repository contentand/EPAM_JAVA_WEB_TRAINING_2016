package com.daniilyurov.training.patterns.memento.tictactoe;

import java.util.Random;

public class AIPlayer extends Player {

    private Random random = new Random(System.currentTimeMillis());

    public AIPlayer(Cell mark) {
        super(mark);
    }

    @Override
    public void setName() {
        name = "Computer";
    }

    @Override
    protected State turnTakingImplementation(String message) {
        int cellIndex1 = random.nextInt(3);
        int cellIndex2 = random.nextInt(3);
        return field.occupy(cellIndex1, cellIndex2, mark);
    }
}
