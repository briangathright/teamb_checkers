package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants.PieceType;

/**
 *
 * Pieces have an owner
 * and a current square
 */
public class Piece {
    private Player belongsTo;
    private int currentSquareIndex;
    private PieceType pieceType;


    public Piece (Player belongsTo, int currentSquareIndex, PieceType pieceType){
        this.belongsTo= belongsTo;
        this.currentSquareIndex = currentSquareIndex;
        this.pieceType = pieceType;
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

    public PieceType getPieceType() {
        return pieceType;
    }
    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}
