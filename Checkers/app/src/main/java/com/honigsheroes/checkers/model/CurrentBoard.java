package com.honigsheroes.checkers.model;

import android.graphics.Rect;

import com.honigsheroes.checkers.Constants;
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


    public void updateDisplay() { //use getters and setters
        listener.update();
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

    public void decreaseNumBlackPieces(){
        numBlackPieces--;
    }
    public void decreaseNumRedPieces(){
        numRedPieces--;
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
            if (removePiece(move)){//if a piece was removed
                if (squares[move.getTargetSquareIndex()].getPiece().getBelongsTo().getColor()== Constants.PlayerColor.BLACK){//if it was black's move
                    decreaseNumRedPieces();//red--
                }
                else {//it was red's move by default
                    decreaseNumBlackPieces();//black--
                }
            }
            convertToKing (move.getTargetSquareIndex());
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
     
     */
    public boolean removePiece(Move move) {

        if (move.getStartSquareIndex()>move.getTargetSquareIndex()) {//moving from red side to black
            if (move.getStartSquareIndex()%8<=4&&move.getStartSquareIndex()%8>0) {//indented row
                if (move.getStartSquareIndex() - move.getTargetSquareIndex() == 9) {//right to left
                    squares[move.getStartSquareIndex() - 4].setPiece(null);//remove
                    return true;
                } else if (move.getStartSquareIndex()-move.getTargetSquareIndex()==7){//left to right
                    squares[move.getStartSquareIndex() - 3].setPiece(null);//remove
                    return true;
                }
            }
            else{//non indented row
                if (move.getStartSquareIndex() - move.getTargetSquareIndex() == 9) {//right to left
                    squares[move.getStartSquareIndex() - 5].setPiece(null);//remove
                    return true;
                }
                else  if (move.getStartSquareIndex()-move.getTargetSquareIndex()==7){//left to right
                    squares[move.getStartSquareIndex()-4].setPiece(null);//remove
                    return true;
                }

            }
        }
        else {//moving from black to red
            if (move.getStartSquareIndex()%8<=4 && move.getStartSquareIndex()%8 >0) {//indented row

                if (move.getTargetSquareIndex() - move.getStartSquareIndex() == 7) {//right to left
                    squares[move.getTargetSquareIndex() - 3].setPiece(null);//remove
                    return true;
                } else  if (move.getTargetSquareIndex()-move.getStartSquareIndex()==9){//left to right
                    squares[move.getTargetSquareIndex() - 4].setPiece(null);//remove
                    return true;
                }
            }
            else{//non indented row
                if (move.getTargetSquareIndex() - move.getStartSquareIndex() == 7) {//right to left
                    squares[move.getTargetSquareIndex() - 4].setPiece(null);//remove
                    return true;
                }
                else {if (move.getTargetSquareIndex()-move.getStartSquareIndex()==9)//left to right
                    squares[move.getTargetSquareIndex()-5].setPiece(null);//remove
                    return true;
                }

            }
        }
    return false;
    }

    /**
     * TODO: Meera
     * TODO: Implement this method to check to see if a piece needs
     * TODO: to become a king and turn it into a king
     */
    public void convertToKing(int squareIndex) {
        if (squares[squareIndex].getPiece().getPieceType()!= Constants.PieceType.KING) {
            if (squares[squareIndex].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.RED){
                if (squareIndex==1 || squareIndex==2 ||squareIndex==3||squareIndex==4){
                    squares[squareIndex].getPiece().setPieceType(Constants.PieceType.KING);
                }

            }
            else if (squares[squareIndex].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.BLACK) {
                if (squareIndex == 29 || squareIndex == 30 || squareIndex == 31 || squareIndex == 32) {
                    squares[squareIndex].getPiece().setPieceType(Constants.PieceType.KING);

                }
            }
        }
    }
}
