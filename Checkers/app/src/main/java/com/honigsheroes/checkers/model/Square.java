package com.honigsheroes.checkers.model;

import android.graphics.Rect;

/**
 * A square is one square on the board.
 * Every square is defined by a rect (its coordinates on the view)
 * and a piece. If no piece is on that square then it has a null
 */
public class Square {
    private Rect rect;
    private Piece piece;
    private Player owner;
    private boolean isActive = false;


    public Square(Rect r, Piece p) {
        rect = r;
        piece = p;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean getActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
