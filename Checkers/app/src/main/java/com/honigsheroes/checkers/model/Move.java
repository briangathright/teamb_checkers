package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants.MoveType;

/**
 *This class implements the moves of the checkers game
 * Moves have a starting square and a target square.
 * They also have a movetype (regular or jump) and if a jump
 * they have the index they are jumping
 */
public class Move {
    private int startSquareIndex;
    private int targetSquareIndex;
    private MoveType moveType = null;
    private int indexOfJumpedSquare = -1;

    public Move(int startSquareIndex) {
        this.startSquareIndex = startSquareIndex;
    }

    public Move (int startSquareIndex, int targetSquareIndex){
        this.startSquareIndex = startSquareIndex;
        this.targetSquareIndex = targetSquareIndex;
    }

    public Move (int startSquareIndex, int targetSquareIndex, MoveType moveType,int indexOfJumpedSquare){
        this.startSquareIndex = startSquareIndex;
        this.targetSquareIndex = targetSquareIndex;
        this.moveType=moveType;
        this.indexOfJumpedSquare=indexOfJumpedSquare;
    }

    public int getStartSquareIndex() {
        return startSquareIndex;
    }

    public void setStartSquareIndex(int startSquareIndex) {
        this.startSquareIndex = startSquareIndex;
    }

    public int getTargetSquareIndex() {
        return targetSquareIndex;
    }

    public void setTargetSquareIndex(int targetSquareIndex) {
        this.targetSquareIndex = targetSquareIndex;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public int getIndexOfJumpedSquare() {
        return indexOfJumpedSquare;
    }

    public void setIndexOfJumpedSquare(int index) {
        this.indexOfJumpedSquare = index;
    }

}
