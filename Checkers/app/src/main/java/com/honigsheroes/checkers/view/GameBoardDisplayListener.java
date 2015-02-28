package com.honigsheroes.checkers.view;

import com.honigsheroes.checkers.model.Move;

/**
 * Listener interface for view, CurrentBoard should subscribe to the listener so it can prompt the
 * View to update itself when moves occur.
 */
public interface GameBoardDisplayListener {
    void update();

    void testUpdate(Move move);
}
