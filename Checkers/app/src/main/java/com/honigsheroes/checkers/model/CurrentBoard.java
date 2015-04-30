package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;

import java.util.ArrayList;

/**
 * The board (internal data structure) for the checkers game.
 * Logs position of pieces and checks to see if they can move etc.
 * notifies view to update whenever an actual change occurs
 */
public class CurrentBoard {
    private Square[] squares;
    private int numRedPieces = 12;
    private int numBlackPieces = 12;
    private boolean blackHasJump = false;
    private boolean redHasJump = false;
    private ArrayList<Move> legalMovesBlack = new ArrayList<Move>();
    private ArrayList<Move> legalMovesRed = new ArrayList<Move>();

    public CurrentBoard(Square[] squares) {
        this.squares = squares;
        calculateLegalMoves();
    }

    public Square[] getSquares() {
        return squares;
    }

    public boolean getHasJumpBlack() {
        return blackHasJump;
    }

    public boolean getHasJumpRed() {
        return redHasJump;
    }

    public int getNumBlackPieces() {
        return numBlackPieces;
    }

    public int getNumRedPieces() {
        return numRedPieces;
    }

    private void decreaseNumBlackPieces() {
        numBlackPieces--;
    }

    private void decreaseNumRedPieces() {
        numRedPieces--;
    }
/**
 *  this uses the board to check the legal moves on the board
 * Its rather verbose, but well documented
  */
    private void calculateLegalMoves() {
        for (int row = 0; row <= 7; row++) {
            System.err.println("Checking row: " + row);
            for (int i = 1; i <= 4; i++) {
                int index = i + row*4;
                System.err.println("Checking square: " + index);
                Square s = squares[index]; //get the current square
                Piece p = s.getPiece(); // get the piece on the square

                if (p == null) { //if there is no piece we skip this square (it can't have moves)
                    System.err.println("skipped : no piece");
                    continue;
                }

                Constants.PlayerColor pc = p.getBelongsTo().getColor(); //get the player color of the piece
                Constants.PieceType pt = p.getPieceType();
                if(row == 7 && pc == Constants.PlayerColor.BLACK && pt == Constants.PieceType.MAN) {
                    System.err.println("skipped : piece is black and trying to move off board");
                    continue;
                }
                if(row == 0 && pc == Constants.PlayerColor.RED && pt == Constants.PieceType.MAN) {
                    System.err.println("skipped : piece is red and trying to move off board");
                    continue;
                }

                if(row == 0 || row == 2 || row == 4 || row == 6) { //a row where the 4th square has only one neighbor

                    if (i == 4) { //4th square only has one neighbor
                        if (pc == Constants.PlayerColor.BLACK) {
                            if (row+1 < 8 && squares[index + 4].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index + 4));
                                System.err.println("added new move");
                            }
                            if (row < 6 && squares[index+4].getPiece() != null && squares[index+4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index+7].getPiece() == null) { //black piece jump
                                legalMovesBlack.add(new Move(index, index+7, Constants.MoveType.JUMP, index+4));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (pt == Constants.PieceType.KING) { //black king regular move
                                if (row-1 > -1 && squares[index - 4].getPiece() == null) {
                                    legalMovesBlack.add(new Move(index, index - 4));
                                    System.err.println("added new move");
                                }
                                if (row > 1 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index-9].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-4));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        }
                        else {
                            if (row-1 > -1 && squares[index - 4].getPiece() == null) { //red piece regular move
                                legalMovesRed.add(new Move(index, index - 4));
                                System.err.println("added new move");
                            }
                            if (row > 1 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index-9].getPiece() == null) { //red piece jump
                                legalMovesRed.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-4));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (row+1 < 8 && pt == Constants.PieceType.KING) { //red king regular move
                                if (squares[index + 4].getPiece() == null) {
                                    legalMovesRed.add(new Move(index, index + 4));
                                    System.err.println("added new move");
                                }
                                if (row < 6 && squares[index+4].getPiece() != null && squares[index+4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index+7].getPiece() == null) { //red king jump
                                    legalMovesRed.add(new Move(index, index+7, Constants.MoveType.JUMP, index+4));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        }
                    } else { //all squares have both neighbors
                        if (pc == Constants.PlayerColor.BLACK) {
                            if (row+1 < 8 && squares[index + 4].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index + 4));
                                System.err.println("added new move");
                            }
                            if (row+1 < 8 && squares[index + 5].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index + 5));
                                System.err.println("added new move");
                            }
                            if(row < 6 && i != 1 && squares[index+4].getPiece() != null && squares[index + 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index+7].getPiece() == null) { //black piece regular jump
                                legalMovesBlack.add(new Move(index, index+7, Constants.MoveType.JUMP, index+4));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if(row < 6 && squares[index+5].getPiece() != null && squares[index + 5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index + 9].getPiece() == null) { //black piece regular jump
                                legalMovesBlack.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+5));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }

                            if (pt == Constants.PieceType.KING) {
                                if (row-1 > -1 && squares[index - 3].getPiece() == null) { //black king regular move
                                    legalMovesBlack.add(new Move(index, index - 3));
                                    System.err.println("added new move");
                                }
                                if (row-1 > -1 && squares[index - 4].getPiece() == null) { //black king regular move
                                    legalMovesBlack.add(new Move(index, index - 4));
                                    System.err.println("added new move");
                                }
                                if (row > 1 && squares[index-3].getPiece() != null && squares[index - 3].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index - 7].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-3));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }
                                if (row > 1 && i != 1 && squares[index-4].getPiece() != null && squares[index - 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index - 9].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-4));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        } else {
                            if (row-1 > -1 && squares[index - 3].getPiece() == null) { //red piece regular move
                                legalMovesRed.add(new Move(index, index - 3));
                                System.err.println("added new move");
                            }
                            if (row-1 > -1 && squares[index - 4].getPiece() == null) { //red piece regular move
                                legalMovesRed.add(new Move(index, index - 4));
                                System.err.println("added new move");
                            }
                            if (row > 1 && squares[index-3].getPiece() != null && squares[index - 3].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index - 7].getPiece() == null) { //red piece regular jump
                                legalMovesRed.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-3));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (row > 1 && i != 1 && squares[index-4].getPiece() != null && squares[index - 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index - 9].getPiece() == null) { //red piece regular jump
                                legalMovesRed.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-4));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (pt == Constants.PieceType.KING) {
                                if (row + 1 < 8 && squares[index + 4].getPiece() == null) { //red king regular move
                                    legalMovesRed.add(new Move(index, index + 4));
                                    System.err.println("added new move");
                                }
                                if (row + 1 < 8 && squares[index + 5].getPiece() == null ) { //red king regular move
                                    legalMovesRed.add(new Move(index, index + 5));
                                    System.err.println("added new move");
                                }
                                if(row < 6 && i != 1 && squares[index+4].getPiece() != null && squares[index+4].getPiece() != null && squares[index + 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index+7].getPiece() == null) { //red king jump
                                    legalMovesRed.add(new Move(index, index+7, Constants.MoveType.JUMP, index+4));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                                if(row < 6 && squares[index+5].getPiece() != null && squares[index+5].getPiece() != null && squares[index + 5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index + 9].getPiece() == null) { //red king jump
                                    legalMovesRed.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+5));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        }
                    }
                }
                else { //row where the 1st square has one neighbor
                    if(i == 1) { //first square only has one neighbor
                        if(pc == Constants.PlayerColor.BLACK) {
                            if (row + 1 < 8 && squares[index + 4].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index+4));
                                System.err.println("added new move");
                            }
                            if (row < 6 && squares[index+4].getPiece() != null && squares[index + 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index + 9].getPiece() == null) { //black piece regular jump
                                legalMovesBlack.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+4));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (pt == Constants.PieceType.KING) {
                                if (row - 1 > -1 && squares[index - 4].getPiece() == null) { //black king move
                                    legalMovesBlack.add(new Move(index, index-4));
                                    System.err.println("added new move");
                                }
                                if (row > 1 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index-7].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-4));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        }
                        else {
                            if (row - 1 > -1 && squares[index - 4].getPiece() == null) { //red piece regular move
                                legalMovesRed.add(new Move(index, index-4));
                                System.err.println("added new move");
                            }
                            if (row > 1 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index-7].getPiece() == null) { //red piece jump
                                legalMovesRed.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-4));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }

                            if(pt == Constants.PieceType.KING) { //red king regular move
                                if (row + 1 < 8 && squares[index + 4].getPiece() == null) {
                                    legalMovesRed.add(new Move(index, index+4));
                                    System.err.println("added new move");
                                }
                                if (row < 6 && squares[index+4].getPiece() != null && squares[index + 4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index + 9].getPiece() == null) { //red king jump
                                    legalMovesRed.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+4));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }

                        }
                    }
                    else { //other squares have two neighbors
                        if(pc == Constants.PlayerColor.BLACK) {
                            if (row + 1 < 8 && squares[index + 3].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index+3));
                                System.err.println("added new move");
                            }
                            if (row + 1 < 8 && squares[index + 4].getPiece() == null) { //black piece regular move
                                legalMovesBlack.add(new Move(index, index+4));
                                System.err.println("added new move");
                            }
                            if (row < 6 && squares[index+3].getPiece() != null && squares[index+3].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index+7].getPiece() == null) { // black piece jump
                                legalMovesBlack.add(new Move(index, index + 7, Constants.MoveType.JUMP, index+3));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (row < 6 && i != 4 && squares[index+4].getPiece() != null && squares[index+4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                    && squares[index+9].getPiece() == null) { // black piece jump
                                legalMovesBlack.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+4));
                                blackHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (pt == Constants.PieceType.KING) {
                                if (row - 1 > -1 && squares[index - 4].getPiece() == null) { //black king regular move
                                    legalMovesBlack.add(new Move(index, index-4));
                                    System.err.println("added new move");
                                }
                                if (row - 1 > -1 && squares[index - 5].getPiece() == null) { //black king regular move
                                    legalMovesBlack.add(new Move(index, index-5));
                                    System.err.println("added new move");
                                }
                                if(row > 1 && i != 4 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index-7].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-4));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }
                                if(row > 1 && squares[index-5].getPiece() != null && squares[index-5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.RED
                                        && squares[index-9].getPiece() == null) { //black king jump
                                    legalMovesBlack.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-5));
                                    blackHasJump = true;
                                    System.err.println("added new jump move");
                                }

                            }
                        }
                        else {
                            if (row - 1 > -1 && squares[index - 4].getPiece() == null) {
                                legalMovesRed.add(new Move(index, index-4)); //red piece regular move
                                System.err.println("added new move");
                            }
                            if (row - 1 > -1 && squares[index - 5].getPiece() == null) {
                                legalMovesRed.add(new Move(index, index-5)); //red piece regular move
                                System.err.println("added new move");
                            }
                            if(row > 1 && i != 4 && squares[index-4].getPiece() != null && squares[index-4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index-7].getPiece() == null) { //red piece jump
                                legalMovesRed.add(new Move(index, index - 7, Constants.MoveType.JUMP, index-4));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if(row > 1 && squares[index-5].getPiece() != null && squares[index-5].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                    && squares[index-9].getPiece() == null) { //red piece jump
                                legalMovesRed.add(new Move(index, index - 9, Constants.MoveType.JUMP, index-5));
                                redHasJump = true;
                                System.err.println("added new jump move");
                            }
                            if (pt == Constants.PieceType.KING) {
                                if (row + 1 < 8 && squares[index + 3].getPiece() == null) { //red king move
                                    legalMovesRed.add(new Move(index, index+3));
                                    System.err.println("added new move");
                                }
                                if (row + 1 < 8 && squares[index + 4].getPiece() == null) { //red king move
                                    legalMovesRed.add(new Move(index, index+4));
                                    System.err.println("added new move");
                                }
                                if (row < 6 && squares[index+3].getPiece() != null && squares[index+3].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index+7].getPiece() == null) { // red king jump
                                    legalMovesRed.add(new Move(index, index + 7, Constants.MoveType.JUMP, index + 3));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                                if (row < 6 && i != 4 && squares[index+4].getPiece() != null && squares[index+4].getPiece().getBelongsTo().getColor() == Constants.PlayerColor.BLACK
                                        && squares[index+9].getPiece() == null) { // red king jump
                                    legalMovesRed.add(new Move(index, index + 9, Constants.MoveType.JUMP, index+4));
                                    redHasJump = true;
                                    System.err.println("added new jump move");
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public ArrayList<Move> getLegalMovesBlack(){
        return legalMovesBlack;
    }

    public ArrayList<Move> getLegalMovesRed() {
        return legalMovesRed;
    }

    /**
     * Actually performs the move on the underlying squares[] data structure, also checks for jump
     */
    public void performMove(Move move) {
        System.err.println("Move " + move.getStartSquareIndex() + " " + move.getTargetSquareIndex() + " " + move.getMoveType() + " " + move.getIndexOfJumpedSquare());
        if (squares[move.getTargetSquareIndex()].getPiece() == null) {
            squares[move.getTargetSquareIndex()].setPiece(squares[move.getStartSquareIndex()].getPiece());
            squares[move.getStartSquareIndex()].setPiece(null);
            if (move.getMoveType()==Constants.MoveType.JUMP){
                removePiece(move);
            }
        }
        blackHasJump=false;
        redHasJump=false;
        legalMovesBlack.clear();
        legalMovesRed.clear();
        calculateLegalMoves();
    }

    /**
     * This function removes the piece that was jumped, it uses the moves jumped index
     */
    private void removePiece(Move move) {
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
     * This function is called everytime a move is made, it checks to see if the move results in a piece
     * needing to become a king.
     * @param squareIndex the square being looked at
     * @return true if the piece can become a king, false otherwise
     */
    public boolean convertToKing(int squareIndex) {
        if (squares[squareIndex].getPiece().getPieceType()!= Constants.PieceType.KING) {
            if (squares[squareIndex].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.RED){
                if (squareIndex==1 || squareIndex==2 ||squareIndex==3||squareIndex==4){
                    squares[squareIndex].getPiece().setPieceType(Constants.PieceType.KING);
                    calculateLegalMoves();
                    return true;
                }

            }
            else if (squares[squareIndex].getPiece().getBelongsTo().getColor()==Constants.PlayerColor.BLACK) {
                if (squareIndex == 29 || squareIndex == 30 || squareIndex == 31 || squareIndex == 32) {
                    squares[squareIndex].getPiece().setPieceType(Constants.PieceType.KING);
                    calculateLegalMoves();
                    return true;
                }
            }
        }
        return false;
    }
}
