package com.honigsheroes.checkers;

import com.honigsheroes.checkers.model.CurrentBoard;
import com.honigsheroes.checkers.model.Square;

/**
 * Location of constants to be used throughout app.
 */
public class Constants {

    /**
     * This enum is used to represent the current state of the game.
     * READY = app is ready to start a new game
     * PLAYING = app is currently playing a game
     * FINISHED = app has finished a game, but isn't READY to start a new one.
     */
    public enum StateOfGame {
        READY, PLAYING, FINISHED
    }

    /**
     * This enum is used to identify player colors.
     * BLACK = player 1, the player with black pieces who goes first
     * RED = play 2, the player with red pieces who goes second
     */
    public enum PlayerColor {
        BLACK, RED
    }

    public static final int UNUSED_SQUARE = 0;
}
