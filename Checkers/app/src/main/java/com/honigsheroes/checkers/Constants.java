package com.honigsheroes.checkers;

/**
 * Location of constants to be used throughout app.
 */
public class Constants {

    /**
     * Constant for any unused square that is clicked (such as red or out of bounds)
     */
    public static final int UNUSED_SQUARE = -1;

    /**
     * This enum is used to represent the current state of the game.
     * READY = app is ready to start a new game
     * PLAYING = app is currently playing a game
     */
    public enum StateOfGame {
        READY, PLAYING
    }

    /**
     * This enum is used to specify the type of a piece
     * MAN = A regular piece, they can only move forward
     * KING = A king piece, they can move forward or backward
     */
    public enum PieceType {
        MAN, KING
    }

    /**
     * This enum is used to identify player colors.
     * BLACK = player 1, the player with black pieces who goes first
     * RED = play 2, the player with red pieces who goes second
     */
    public enum PlayerColor {
        BLACK, RED
    }

    /**REGULAR
     * This enum specifies the game type.
     * HUMAN = two humans playing on the same device
     * AI = one human vs one AI
     * REMOTE = two humans on separate devices (NOT IMPLEMENTED)
     */
    public enum GameType {
        HUMAN, AI, REMOTE
    }

    /**
     * This enum specifies the type of a move.
     * STANDARD = A regular move forward (or backward) one square
     * JUMP = A jump move forward (or backward) any piece jumped is captured
     */
    public enum MoveType {
        STANDARD, JUMP
    }
}
