package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants.PieceType;

/**
 *
 * Pieces have an owner, the player they belong to
 * and a current square that they are on
 */
public class Piece {
    private Player belongsTo;
    private PieceType pieceType;


    public Piece (Player belongsTo, PieceType pieceType){
        this.belongsTo= belongsTo;
        this.pieceType = pieceType;
    }

    public Player getBelongsTo(){
        return belongsTo;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}
