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
     * TODO: MEERA
     * TODO: Add king moves to list.
     * TODO: THIAGO
     * TODO: test logic once checkLegalMoves implemented
     *
     */
    public void calculateLegalMoves() {
        for (int i =1;i<=32;i++) {
            if(squares[i].getPiece()!=null) {
                if (squares[i].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK) {
                    if (squares[i].getPiece().getPieceType() == Constants.PieceType.MAN && i < 29) { //black regular piece not in last row
                        if ((i % 8 != 4)) {
                            if (squares[i + 5].getPiece() == null) {
                                legalMovesBlack.add(new Move(i, i + 5));
                            } else if (i < 25 && squares[i + 5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED && i % 8 != 0) {//black regular piece not in 2nd to last row
                                if (squares[i + 9].getPiece() == null) {
                                    legalMovesBlack.add(new Move(i, i + 9, Constants.MoveType.JUMP, i + 5));
                                    blackHasJump = true;
                                }
                            }
                        }
                        if (i % 8 != 1) {
                            if (squares[i + 4].getPiece() == null) {
                                legalMovesBlack.add(new Move(i, i + 4));
                            } else if (squares[i + 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED && i % 8 != 5) {
                                if (i < 25) {
                                    if (squares[i + 7].getPiece() == null) {
                                        legalMovesBlack.add(new Move(i, i + 9, Constants.MoveType.JUMP, i + 4));
                                        blackHasJump = true;
                                    }
                                }

                            }
                        }

                    }

                } else if (squares[i].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED) {
                    if (squares[i].getPiece().getPieceType() == Constants.PieceType.MAN && i > 4) { //black regular piece not in last row
                        if ((i % 8 != 4)) {
                            if (squares[i - 4].getPiece() == null) {
                                legalMovesRed.add(new Move(i, i - 4));
                            } else if (i > 8 && squares[i - 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK && i % 8 != 0) {//black regular piece not in 2nd to last row
                                if (squares[i - 7].getPiece() == null) {
                                    legalMovesRed.add(new Move(i, i - 7, Constants.MoveType.JUMP, i - 4));
                                    redHasJump = true;
                                }
                            }
                        }
                        if (i % 8 != 1) {
                            if (squares[i - 5].getPiece() == null) {
                                legalMovesRed.add(new Move(i, i - 5));
                            } else if (i > 8 && squares[i - 5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED && i % 8 != 5) {
                                if (squares[i - 9].getPiece() == null) {
                                    legalMovesRed.add(new Move(i, i - 9, Constants.MoveType.JUMP, i - 5));
                                    redHasJump = true;
                                }

                            }
                        }

                    }

                }

            }

        }

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
            if (move.getMoveType()==Constants.MoveType.JUMP){
                removePiece(move);
            }
            convertToKing (move.getTargetSquareIndex());
        }
        blackHasJump=false;
        redHasJump=false;
        legalMovesBlack.clear();
        legalMovesRed.clear();
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


    public void removePiece(Move move) {
        if (squares[move.getIndexOfJumpedSquare()].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.BLACK){
            squares[move.getIndexOfJumpedSquare()].setPiece(null);
            decreaseNumBlackPieces();
        }
        else if (squares[move.getIndexOfJumpedSquare()].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.RED){
            squares[move.getIndexOfJumpedSquare()].setPiece(null);
            decreaseNumRedPieces();
        }
    }

    /**

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
