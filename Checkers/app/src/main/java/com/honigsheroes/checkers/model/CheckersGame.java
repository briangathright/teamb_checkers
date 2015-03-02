package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;
/**
 * TODO: Thiago implement CheckersGame class
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame {

    private CurrentBoard board;
    private Move move;
    private int firstSquareIndex = 0;
    private int secondSquareIndex = 0;
    private int activeSquareIndex = -1;
    public int getHilight () {
        return activeSquareIndex;
    }
    public void setHilight(int index){
        this.activeSquareIndex = index;
    }
    public CheckersGame(CurrentBoard board) {
        this.board = board;
    }

    public void onClick(int touchedSquareIndex) {
        //most of this logic should probably be moved to the currentboard
        //I think just create the move here and pass that to board to do actual moving.
        if(touchedSquareIndex == Constants.UNUSED_SQUARE) {
            return;
        }
        if(firstSquareIndex == 0 && board.getSquares()[touchedSquareIndex].getPiece() != null) {
            firstSquareIndex = touchedSquareIndex;
            setHilight(firstSquareIndex);
            return;
        }
        else {
            secondSquareIndex = touchedSquareIndex;
            if(firstSquareIndex == secondSquareIndex) {
                return;
            }
            if(firstSquareIndex == 0 || secondSquareIndex == 0) {
                return;
            }
            if (board.getSquares()[secondSquareIndex].getPiece() == null) {
                board.getSquares()[secondSquareIndex].setPiece(board.getSquares()[firstSquareIndex].getPiece());
                board.getSquares()[firstSquareIndex].setPiece(null);
            }
            setHilight (-1);
            firstSquareIndex = 0;
            secondSquareIndex = 0;
            board.updateDisplay();
        }
//        if(getMove()==null){
//            this.move = new Move(touchedSquareIndex);
//        }
//        else{
//            getMove().setTargetSquareIndex(touchedSquareIndex);
//            Piece piece = getBoard().getSquares()[getMove().getStartSquareIndex()].getPiece();
//            board.getSquares()[getMove().getStartSquareIndex()].getPiece().setCurrentSquareIndex(getMove().getTargetSquareIndex());
//            System.out.println("Current position: "+piece.getCurrentSquareIndex());
//            board.updateDisplay();
//            move = null;
//        }
//        board.updateDisplay();
//        //getBoard().testUpdate(touchedSquareIndex);
    }

    public CurrentBoard getBoard() {
        return board;
    }

    public void setBoard(CurrentBoard board) {
        this.board = board;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
