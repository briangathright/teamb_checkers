package com.honigsheroes.checkers.model;

import android.graphics.Rect;

import com.honigsheroes.checkers.view.GameBoardDisplayListener;

/**
 * TODO: Thiago implement GameBoard class
 * The board (internal data structure) for the checkers game.
 * Logs position of pieces and checks to see if they can move etc.
 * notifies view to update whenever an actual change occurs
 */
public class GameBoard {
    private GameBoardDisplayListener listener;
    private Square[] squares;

    public GameBoard(Square[] squares, GameBoardDisplayListener listener) {
        this.squares = squares;
        this.listener = listener;
    }

    public void testUpdate(int squareIndex) {
        if(squareIndex==0) { return;}
//        squares[squareIndex].setOwner("Neither");
        updateDisplay();
    }

    public void updateDisplay() { //use getters and setters
        listener.update();
    }
}
