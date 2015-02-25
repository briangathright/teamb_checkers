package com.honigsheroes.checkers.model;

/**
 *
 * Moves have a starting square and a target square.
 */
public class Move {
    private int startSquareIndex;
    private int targetSquareIndex;
    public Move (int startSquareIndex, int targetSquareIndex){
        this.startSquareIndex = startSquareIndex;
        this.targetSquareIndex = targetSquareIndex;
    }
    public int getStartSquareIndex(){
        return startSquareIndex;
    }
    public int getTargetSquareIndex(){
        return targetSquareIndex;
    }
}
