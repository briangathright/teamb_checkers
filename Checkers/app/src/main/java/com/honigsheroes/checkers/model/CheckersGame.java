package com.honigsheroes.checkers.model;


/**
 * TODO: Thiago implement CheckersGame class
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame {

    private CurrentBoard board;
    private Move move;

    public CheckersGame(CurrentBoard board) {
        this.board = board;
    }

    public void onClick(int touchedSquareIndex) {
        if(getMove()==null){
            this.move = new Move(touchedSquareIndex);
        }
        else{
            getMove().setTargetSquareIndex(touchedSquareIndex);
            Piece piece = getBoard().getSquares()[getMove().getStartSquareIndex()].getPiece();
            piece.setCurrentSquareIndex(getMove().getTargetSquareIndex());
            System.out.println("Current position: "+piece.getCurrentSquareIndex());
            board.testUpdate(move);
            move = null;
        }
        getBoard().testUpdate(touchedSquareIndex);
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
