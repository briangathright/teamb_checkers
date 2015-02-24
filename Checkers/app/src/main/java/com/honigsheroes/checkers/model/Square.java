package com.honigsheroes.checkers.model;

import android.graphics.Rect;

/**
 * A square is one square on the board.
 * Every square is defined by a rect (its coordinates on the view)
 * and a piece. If no piece is on that square then it has a null.
 * TODO: fix class once Piece is implemented
 */
public class Square {
    private Rect rect;
    private Piece piece;
    public String owner; //temporary for testing display

    //temporary constructor for testing display
    public Square(Rect r, String owner) {
        rect = r;
        this.owner = owner;
    }

    public Square(Rect r, Piece p) {
        rect = r;
        piece = p;
    }

    //temporary for testing display
    public void setOwner(String string) {
        this.owner = string;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Rect getRect() {
        return rect;
    }

    public Piece getPiece() {
        return piece;
    }
}
