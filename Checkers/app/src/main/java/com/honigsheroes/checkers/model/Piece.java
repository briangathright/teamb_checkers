package com.honigsheroes.checkers.model;

/**
 *
 * Pieces have an owner
 * and a current square
 */
public class Piece {
    private Player belongsTo;
    private int currentSquareIndex;


    public Piece (Player belongsTo, int currentSquareIndex){
        this.belongsTo= belongsTo;
        this.currentSquareIndex = currentSquareIndex;
    }

    public Player getBelongsTo(){
        return belongsTo;


    }
    public int getCurrentSquareIndex()
    {
        return currentSquareIndex;
    }


    public void setCurrentSquareIndex(int currentSquareIndex){
        this.currentSquareIndex = currentSquareIndex;
    }
}
