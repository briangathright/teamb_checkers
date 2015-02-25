package com.honigsheroes.checkers.model;

/**
 * TODO: Meera implement Piece class.
 * Pieces have an owner
 * and a current square
 */
public class Piece {
    public Player belongsTo;
    public int currentSquare;
    public Piece (Player belongsTo, int currentSquare){
        this.belongsTo= belongsTo;
        this.currentSquare = currentSquare;
    }

}
