package com.honigsheroes.checkers.model;

import android.graphics.Rect;

import com.honigsheroes.checkers.view.GameBoardDisplayListener;

/**
 * TODO: Thiago implement CurrentBoard class
 * The board (internal data structure) for the checkers game.
 * Logs position of pieces and checks to see if they can move etc.
 * notifies view to update whenever an actual change occurs
 */
public class CurrentBoard {
    private GameBoardDisplayListener listener;
    private Square[] squares;

    public CurrentBoard(Square[] squares, GameBoardDisplayListener listener) {
        this.squares = squares;
        this.listener = listener;
    }

//    public void testUpdate(int squareIndex) {
//        if(squareIndex==0) {
//            return;
//        }
//        updateDisplay();
//    }

    public void updateDisplay() { //use getters and setters
        listener.update();
    }

//    update void testUpdate(Move move){
//        listener.testUpdate(move);
//    }

    public Square[] getSquares() {
        return squares;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

}
