package com.honigsheroes.checkers.model;

import android.graphics.Rect;

import com.honigsheroes.checkers.view.GameBoardDisplayListener;

import java.util.ArrayList;

/**
 * The board (internal data structure) for the checkers game.
 * Logs position of pieces and checks to see if they can move etc.
 * notifies view to update whenever an actual change occurs
 */
public class CurrentBoard {
    private GameBoardDisplayListener listener;
    private Square[] squares;
    private int numRedPieces = 12;
    private int numBlackPieces = 12;
    private boolean blackHasJump = false;
    private boolean redHasJump = false;
    private ArrayList<Move> legalMovesBlack = new ArrayList<Move>();
    private ArrayList<Move> legalMovesRed = new ArrayList<Move>();

    public CurrentBoard(Square[] squares, GameBoardDisplayListener listener) {
        this.squares = squares;
        this.listener = listener;
    }


    public void updateDisplay(String errorMessage) { //use getters and setters
        listener.update(errorMessage);
    }

    public Square[] getSquares() {
        return squares;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

    public int getNumBlackPieces() {
        return numBlackPieces;
    }

    public int getNumRedPieces() {
        return numRedPieces;
    }

    /**
     * TODO: Thiago
     * TODO: start working on calculating all legal moves
     * TODO: focus on just regular pieces and their jumps
     */
    public void calculateLegalMoves() {

    }

    public ArrayList<Move> getMovesBlack(){
        return legalMovesBlack;
    }

    public ArrayList<Move> getLegalMovesRed() {
        return legalMovesRed;
    }

    public void performMove(Move move) {
        if (squares[move.getTargetSquareIndex()].getPiece() == null) {
            squares[move.getTargetSquareIndex()].setPiece(squares[move.getStartSquareIndex()].getPiece());
            squares[move.getStartSquareIndex()].setPiece(null);
        }

        if (checkFollowUpJump(move.getTargetSquareIndex())) {
        //if there is followup jumps
        }
    }

    /**
     * TODO: after we get the basics working
     */
    public boolean checkFollowUpJump(int squareIndex) {
        return false;
    }

    /**
     * TODO: Rakesh
     * TODO: implement this method
     * TODO: it should remove the piece at the index
     * TODO: and update the number of pieces for that player
     */
    public void removePiece(int squareIndex) {

    }

    /**
     * TODO: Meera
     * TODO: Implement this method to check to see if a piece needs
     * TODO: to become a king and turn it into a king
     */
    public void convertToKing(int squareIndex) {

    }
}
