package com.honigsheroes.checkers.model;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;
/**
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame{



    private CurrentBoard board;
    private Move move;
    private int firstSquareIndex = -1;
    private int secondSquareIndex = -1;
    private PlayerColor playerTurn= PlayerColor.BLACK;

    public CheckersGame(CurrentBoard board) {

        this.board = board;
    }

    public void onClick(int touchedSquareIndex) {
        //most of this logic should probably be moved to the currentboard
        //I think just create the move here and pass that to board to do actual moving.
        if(touchedSquareIndex == Constants.UNUSED_SQUARE) {
            firstSquareIndex = Constants.UNUSED_SQUARE;
            secondSquareIndex = Constants.UNUSED_SQUARE;
            board.updateDisplay();
            return;
        }
        if(firstSquareIndex == Constants.UNUSED_SQUARE && board.getSquares()[touchedSquareIndex].getPiece() != null&& board.getSquares()[touchedSquareIndex].getPiece().getBelongsTo().getColor()==playerTurn) {
            firstSquareIndex = touchedSquareIndex;

            return;
        }
        else {

            secondSquareIndex = touchedSquareIndex;

            if(firstSquareIndex == secondSquareIndex) {
                board.updateDisplay();
                firstSquareIndex = Constants.UNUSED_SQUARE;
                secondSquareIndex = Constants.UNUSED_SQUARE;
                return;
            }
            if(firstSquareIndex == Constants.UNUSED_SQUARE) {
                board.updateDisplay();
                secondSquareIndex = Constants.UNUSED_SQUARE;
                return;
            }
            }

            if (board.getSquares()[secondSquareIndex].getPiece() == null) {
                board.getSquares()[secondSquareIndex].setPiece(board.getSquares()[firstSquareIndex].getPiece());
                board.getSquares()[firstSquareIndex].setPiece(null);
                if (playerTurn == PlayerColor.BLACK) {
                    playerTurn = PlayerColor.RED;
                }else{
                    playerTurn = PlayerColor.BLACK;
                }
                }


            firstSquareIndex = Constants.UNUSED_SQUARE;
            secondSquareIndex = Constants.UNUSED_SQUARE;
            board.updateDisplay();
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
