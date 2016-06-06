package com.daniilyurov.training.patterns.memento.tictactoe;

public class Game {
    private Player[] players = new Player[2];

    public Game() {
        players[0] = new HumanPlayer(Cell.O);
        players[1] = new AIPlayer(Cell.X);
        players[0].setName();
        players[1].setName();
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
    }

    public void start() {
        //noinspection InfiniteLoopStatement
        boolean saving = true;
        while (true) {
            if (saving) {
                players[0].save();
                saving = false;
            } else {
                saving = true;
            }
            State result = State.INIT;
            for (Player player: players) {
                result = player.takeTurn();
            }
            if (result == State.QUIT){
                break;
            }
        }
    }
}
