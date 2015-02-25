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

    public int findSquareIndex(int x, int y) {
        for(int i = 1; i < squares.length; i++) {
            Rect rect = squares[i].getRect();
            if(rect.contains(x, y)) {
                testUpdate(i);
                return i;
            }
        }
        return 0;
    }

    public void testUpdate(int squareIndex) {
        squares[squareIndex].setOwner("Neither");
        updateDisplay();
    }

    public void updateDisplay() { //use getters and setters
        listener.update();
    }
}
