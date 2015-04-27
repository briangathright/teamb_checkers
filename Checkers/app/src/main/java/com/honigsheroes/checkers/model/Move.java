package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants.MoveType;

/**
 *This class implements the moves of the checkers game
 * Moves have a starting square and a target square.
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

    /**
     *This constructs the checkers with the initial position ,
     * the target position, the moves of the checkers and the index of the jumped square
     * @param startSquareIndex
     * @param targetSquareIndex
     * @param moveType
     * @param indexOfJumpedSquare
     */
    public Move (int startSquareIndex, int targetSquareIndex, MoveType moveType,int indexOfJumpedSquare){
        this.startSquareIndex = startSquareIndex;
        this.targetSquareIndex = targetSquareIndex;
        this.moveType=moveType;
        this.indexOfJumpedSquare=indexOfJumpedSquare;
    }

    /**
     *This function gets the starting square of the checkers game
     *
     * @return startSquareIndex This is the initial square of the checkers
     * from where it is moved to the target square
     */
    public int getStartSquareIndex() {
        return startSquareIndex;
    }

    public void setStartSquareIndex(int startSquareIndex) {
        this.startSquareIndex = startSquareIndex;
    }


    /**
     * The checkers is moved to the target square
     * @return targetSquareIndex this is the target
     */
    public int getTargetSquareIndex() {
        return targetSquareIndex;
    }

    public void setTargetSquareIndex(int targetSquareIndex) {
        this.targetSquareIndex = targetSquareIndex;
    }

    /**The checkers are moved and jumped
     *
     * @return moveType this does the checkers move or jump
     */
    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    /**The checker pieces are captured
     *
     * @return indexOfJumpedSquare this is used to capture the pieces
     */
    public int getIndexOfJumpedSquare() {
        return indexOfJumpedSquare;
    }

    /**The parameter index is the index of the jumped square
     *
     * @param index is the index of the Jumped Square
     */
    public void setIndexOfJumpedSquare(int index) {
        this.indexOfJumpedSquare = index;
    }

}
