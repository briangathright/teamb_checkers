package com.honigsheroes.checkers.model;

/**
 * TODO: Meera implement move class as per class diagram.
 * Moves have a starting square and a target square.
 */
public class Move {
    public int startSquare;
    public int targetSquare;
    public Move (int startSquare, int targetSquare){
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
    }
}
