package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame{

    private Context context;
    private CurrentBoard board;
    private Move move = new Move(-1, -1);
    private int firstSquareIndex = -1;
    private int secondSquareIndex = -1;
    private int followUpJumpIndex = -1;
    private boolean followUpJump = false;
    private Player playerOne;
    private Player playerTwo;
    private GameType gameType;
    private PlayerColor playerTurn= PlayerColor.BLACK;

    private Toast mToast;

    public CheckersGame(Context context, CurrentBoard board, Player playerOne, Player playerTwo, GameType gameType) {
        this.board = board;
        this.context = context;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameType = gameType;
        this.board.brianCalculateLegalMoves();
        displayMessage(playerOne.getName() + "'s turn to move.");
    }

    public void displayMessage(String message) {
        if(mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), message , Toast.LENGTH_SHORT);
        }
        else {
            mToast.setText(message);
        }
        mToast.show();

    }
    public void onClick(int touchedSquareIndex) {
        if(touchedSquareIndex == 0) {

            return;
        }
        if(playerTurn == PlayerColor.RED && gameType == GameType.AI){
            return;
        }

        if(followUpJump) {
            firstSquareIndex = followUpJumpIndex;
            move.setStartSquareIndex(firstSquareIndex);
            board.getSquares()[firstSquareIndex].setActive(true);
            secondSquareIndex = touchedSquareIndex;
            board.updateDisplay();
            if(secondSquareIndex != -1) {
                move.setTargetSquareIndex(secondSquareIndex);
                performMove();
                board.getSquares()[firstSquareIndex].setActive(false);
                if(followUpJump) {
                    firstSquareIndex = followUpJumpIndex;
                    secondSquareIndex = Constants.UNUSED_SQUARE;
                    move.setStartSquareIndex(firstSquareIndex);
                    board.getSquares()[firstSquareIndex].setActive(true);
                }
                else{
                    board.getSquares()[firstSquareIndex].setActive(false);
                    firstSquareIndex = Constants.UNUSED_SQUARE;
                    secondSquareIndex = Constants.UNUSED_SQUARE;
                }
                board.updateDisplay();
                return;
            }
            else{
                displayMessage("You must continue the jump.");
                return;
            }
        }

        if(touchedSquareIndex == Constants.UNUSED_SQUARE) { //if a non black square is touched
            if(firstSquareIndex != Constants.UNUSED_SQUARE) { //if this is the 2nd square clicked
                board.getSquares()[firstSquareIndex].setActive(false); //unhighlight the first square
            }
            firstSquareIndex = Constants.UNUSED_SQUARE; //reset the first square
            secondSquareIndex = Constants.UNUSED_SQUARE; //reset the second square
            //displayMessage("You may only select black squares.");
            board.updateDisplay();
            return;
        }
        if(firstSquareIndex == Constants.UNUSED_SQUARE //if this is the first square touched
                && board.getSquares()[touchedSquareIndex].getPiece() != null //and there is a piece there
                && board.getSquares()[touchedSquareIndex].getPiece().getBelongsTo().getColor()==playerTurn) { //and the piece belongs to the player with control
            firstSquareIndex = touchedSquareIndex; //set the first square
            board.getSquares()[firstSquareIndex].setActive(true); //highlight the square
            board.updateDisplay();
            return;
        }
        else { //else its the second square touched

            secondSquareIndex = touchedSquareIndex; //set the second square

            if(firstSquareIndex == Constants.UNUSED_SQUARE) { //if the first square is not set
                //displayMessage("You may only select your own pieces.");
                board.updateDisplay();
                secondSquareIndex = Constants.UNUSED_SQUARE;
                return;
            }

            if(firstSquareIndex == secondSquareIndex) { //if the first and 2nd are the same
                board.getSquares()[firstSquareIndex].setActive(false); //unhighlight
                board.updateDisplay();
                firstSquareIndex = Constants.UNUSED_SQUARE; //reset square
                secondSquareIndex = Constants.UNUSED_SQUARE; //reset square
                return;
            }

        }
        if(board.getSquares()[secondSquareIndex].getPiece() != null) {
            board.getSquares()[firstSquareIndex].setActive(false); //unhighlight
            firstSquareIndex = Constants.UNUSED_SQUARE;
            secondSquareIndex = Constants.UNUSED_SQUARE;
            //displayMessage("You may only move to unoccupied black squares.");
            board.updateDisplay();
            return;
        }
        move.setStartSquareIndex(firstSquareIndex);
        move.setTargetSquareIndex(secondSquareIndex);
        performMove();

        if(followUpJump) {
            firstSquareIndex = followUpJumpIndex;
            secondSquareIndex = Constants.UNUSED_SQUARE;
            move.setStartSquareIndex(firstSquareIndex);
            board.getSquares()[firstSquareIndex].setActive(true);
        }
        else{
            board.getSquares()[firstSquareIndex].setActive(false);
            firstSquareIndex = Constants.UNUSED_SQUARE;
            secondSquareIndex = Constants.UNUSED_SQUARE;
        }
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

    public boolean checkFollowUpJump(int squareIndex) {
        Square s = board.getSquares()[squareIndex];
        Piece p = s.getPiece();
        Constants.PlayerColor pc = p.getBelongsTo().getColor();
        if(pc == Constants.PlayerColor.RED) {
            for(Move m : board.getLegalMovesRed()) {
                if (m.getStartSquareIndex() == squareIndex && m.getMoveType() == Constants.MoveType.JUMP) {
                    return true;
                }
            }
        }
        else if(pc == Constants.PlayerColor.BLACK) {
            for(Move m : board.getLegalMovesBlack()) {
                if (m.getStartSquareIndex() == squareIndex && m.getMoveType() == Constants.MoveType.JUMP) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfLegalMove() {
        boolean legal = false;

        if(playerTurn == PlayerColor.BLACK) {
            for(Move m : board.getLegalMovesBlack()) {
                if (m.getStartSquareIndex() == move.getStartSquareIndex() &&
                        m.getTargetSquareIndex() == move.getTargetSquareIndex()) {
                    if(!board.getHasJumpBlacK()) {
                        move.setMoveType(MoveType.STANDARD);
                        legal = true;
                        break;
                    }
                    else {
                        if(m.getMoveType() == MoveType.JUMP) {
                            move.setMoveType(MoveType.JUMP);
                            move.setIndexOfJumpedSquare(m.getIndexOfJumpedSquare());
                            legal = true;
                            break;
                        }
                    }
                }


            }
        }
        else {
            for(Move m : board.getLegalMovesRed()) {

                if (m.getStartSquareIndex() == move.getStartSquareIndex() &&
                        m.getTargetSquareIndex() == move.getTargetSquareIndex()) {
                    if(!board.getHasJumpRed()) {
                        move.setMoveType(MoveType.STANDARD);
                        legal = true;
                        break;
                    }
                    else {
                        if(m.getMoveType() == MoveType.JUMP) {
                            move.setMoveType(MoveType.JUMP);
                            move.setIndexOfJumpedSquare(m.getIndexOfJumpedSquare());
                            legal = true;
                            break;
                        }
                    }
                }
            }
        }
        return legal;
    }

    public void performMove(){
        if(checkIfLegalMove()) {
            board.performMove(move);
            board.getSquares()[move.getStartSquareIndex()].setActive(false);

            if(board.convertToKing(move.getTargetSquareIndex())) {
                followUpJump = false;
                board.brianCalculateLegalMoves();
                startNextTurn();
                return;
            }

            if(move.getMoveType() == MoveType.JUMP && checkFollowUpJump(move.getTargetSquareIndex())) {
                followUpJumpIndex = move.getTargetSquareIndex();
                followUpJump = true;
            }
            else {
                followUpJump = false;
                followUpJumpIndex = -1;
                startNextTurn();
                return;
            }
        }
        else {
            if(board.getHasJumpBlacK() && playerTurn == PlayerColor.BLACK) {
                displayMessage("You must Jump!");
            }
            else if(board.getHasJumpRed() && playerTurn == PlayerColor.RED) {
                displayMessage("You must Jump!");
            }
            else {
                System.out.println("Start index : " +  move.getStartSquareIndex());
                System.out.println("Target index : " + move.getTargetSquareIndex());
                displayMessage("Not a legal move, try again");
            }

            return;
        }
    }

    public void startNextTurn() {

        if(!checkWinConditions()) {
            move = new Move(-1, -1);
            if (playerTurn == PlayerColor.BLACK) {
                playerTurn = PlayerColor.RED;
                displayMessage(playerTwo.getName() + "'s turn to move.");
            } else {
                displayMessage(playerOne.getName() + "'s turn to move.");
                playerTurn = PlayerColor.BLACK;
            }

            if (gameType == GameType.AI && playerTurn == PlayerColor.RED) {
                //moveAI();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    moveAI(); } }, 1000);
            }
        }

    }


    public boolean checkWinConditions() {
        if (board.getNumBlackPieces()<=0 || board.getLegalMovesBlack().isEmpty()){
            displayMessage(playerTwo.getName() + " is the winner!");
            return true;
        }
        else if (board.getNumRedPieces()<=0 || board.getLegalMovesRed().isEmpty()){
            displayMessage(playerOne.getName() + " is the winner!");
            return true;
        }
        return false;
    }

    /**
     * TODO: Brian
     */
    public void moveAI() {
        ArrayList<Move> allMoves = board.getLegalMovesRed();
        ArrayList<Move> allJumps = new ArrayList<Move>();
        for(Move m : allMoves) {
            if(m.getMoveType() == MoveType.JUMP) {
                allJumps.add(m);
            }
        }
        if(allMoves.isEmpty()){
            return;
        }
        Random rand = new Random();
        if(allJumps.isEmpty()){
            int index = rand.nextInt(allMoves.size());
            move = allMoves.get(index);
        }
        else {
            if (followUpJump) {
                for (Move m : allJumps) {
                    if (m.getStartSquareIndex() == followUpJumpIndex) {
                        move = m;
                    }
                }
            } else {
                int index = rand.nextInt(allJumps.size());
                move = allJumps.get(index);
            }
        }

        performMove();
        board.updateDisplay();
//        if(followUpJump) {
//            moveAI();
//        }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(followUpJump) {
                    moveAI(); } } }, 1000);

    }
}
