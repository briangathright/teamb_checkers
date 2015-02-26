package com.honigsheroes.checkers.model;


/**
 * TODO: Thiago implement CheckersGame class
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame {

    private CurrentBoard board;

    public CheckersGame(CurrentBoard board) {
        this.board = board;
    }

    public void onClick(int touchedSquareIndex) {
        board.testUpdate(touchedSquareIndex);
    }
}
