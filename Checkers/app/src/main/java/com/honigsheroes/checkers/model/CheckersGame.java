package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;
/**
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame{



    private CurrentBoard board;
    private Move move = new Move(-1, -1);
    private int firstSquareIndex = -1;
    private int secondSquareIndex = -1;
    private Player playerOne;
    private Player playerTwo;
    private GameType gameType;
    private PlayerColor playerTurn= PlayerColor.BLACK;


    public CheckersGame(CurrentBoard board, Player playerOne, Player playerTwo, GameType gameType) {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameType = gameType;
    }

    public void onClick(int touchedSquareIndex) {
        if(touchedSquareIndex == 0) {
            
            return;
        }
        if(touchedSquareIndex == Constants.UNUSED_SQUARE) { //if a non black square is touched
            if(firstSquareIndex != Constants.UNUSED_SQUARE) { //if this is the 2nd square clicked
                board.getSquares()[firstSquareIndex].setActive(false); //unhighlight the first square
            }
            firstSquareIndex = Constants.UNUSED_SQUARE; //reset the first square
            secondSquareIndex = Constants.UNUSED_SQUARE; //reset the second square
            board.updateDisplay("You may only select black squares.");
            return;
        }
        if(firstSquareIndex == Constants.UNUSED_SQUARE //if this is the first square touched
                && board.getSquares()[touchedSquareIndex].getPiece() != null //and there is a piece there
                && board.getSquares()[touchedSquareIndex].getPiece().getBelongsTo().getColor()==playerTurn) { //and the piece belongs to the player with control
            firstSquareIndex = touchedSquareIndex; //set the first square
            board.getSquares()[firstSquareIndex].setActive(true); //highlight the square
            board.updateDisplay(null);
            return;
        }
        else { //else its the second square touched

            secondSquareIndex = touchedSquareIndex; //set the second square

            if(firstSquareIndex == Constants.UNUSED_SQUARE) { //if the first square is not set
                board.updateDisplay("You may only select your own pieces.");
                secondSquareIndex = Constants.UNUSED_SQUARE;
                return;
            }

            if(firstSquareIndex == secondSquareIndex) { //if the first and 2nd are the same
                board.getSquares()[firstSquareIndex].setActive(false); //unhighlight
                board.updateDisplay(null);
                firstSquareIndex = Constants.UNUSED_SQUARE; //reset square
                secondSquareIndex = Constants.UNUSED_SQUARE; //reset square
                return;
            }

        }
        if(board.getSquares()[secondSquareIndex].getPiece() != null) {
            board.getSquares()[firstSquareIndex].setActive(false); //unhighlight
            firstSquareIndex = Constants.UNUSED_SQUARE;
            secondSquareIndex = Constants.UNUSED_SQUARE;
            board.updateDisplay("You may only move to unoccupied black squares.");
            return;
        }
        move.setStartSquareIndex(firstSquareIndex);
        move.setTargetSquareIndex(secondSquareIndex);
        performMove(move);


        board.getSquares()[firstSquareIndex].setActive(false);
        firstSquareIndex = Constants.UNUSED_SQUARE;
        secondSquareIndex = Constants.UNUSED_SQUARE;
        board.updateDisplay(null);
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

    /**
     * TODO: Thiago
     * TODO: implement method to compare the move to the movelist for the player whose turn it is
     * TODO: then we can figure out if its a legal move and return true/false accordingly
     */
    public boolean checkIfLegalMove(Move move) {
        return true;
    }

    public void performMove(Move move){
        if(checkIfLegalMove(move)) {
            board.performMove(move);
            startNextTurn();
        }
        else {
            return;
        }
    }

    public void startNextTurn() {
        board.calculateLegalMoves();

        if(checkWinConditions()) {
            //display winner etc
        }

        if(playerTurn == PlayerColor.BLACK) {
            playerTurn = PlayerColor.RED;
        }
        else {
            playerTurn = PlayerColor.BLACK;
        }

        if(gameType == GameType.AI && playerTurn == PlayerColor.RED) {
            moveAI();
        }
    }

    /**
     * TODO: Thiago
     * TODO: add the checks here to see who wins
     */
    public boolean checkWinConditions() {
        return false;
    }

    /**
     * TODO: Brian
     */
    public void moveAI() {

    }

}
