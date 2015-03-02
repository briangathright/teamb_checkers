package com.honigsheroes.checkers.model;

import android.graphics.Rect;

import com.honigsheroes.checkers.view.GameBoardDisplayListener;

/**
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


    public void updateDisplay() { //use getters and setters
        listener.update();
    }



    public Square[] getSquares() {
        return squares;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

}
