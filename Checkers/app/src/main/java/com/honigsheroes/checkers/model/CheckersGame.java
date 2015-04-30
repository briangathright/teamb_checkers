package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;
import com.honigsheroes.checkers.R;
import com.honigsheroes.checkers.view.GameBoardDisplayListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * The model for the checker game.
 * A checkers game has 2 players, and a current board.
 */
public class CheckersGame{

    private Context context;
    private GameBoardDisplayListener listener;
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
    public CheckersGame(Context context, GameBoardDisplayListener listener, CurrentBoard board, Player playerOne, Player playerTwo, GameType gameType) {
        this.board = board;
        this.listener = listener;
        this.context = context;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameType = gameType;
        displayMessage(playerOne.getName() + "'s turn to move.");
    }

    /**
     * tells the display associated with this model to update itself
     */
    private void updateDisplay() { //use getters and setters
        listener.update();
    }

    /**
     * displays a given message as a toast
     */
    private void displayMessage(String message) {
        if(mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), message , Toast.LENGTH_SHORT);
            View view = mToast.getView();
            view.setBackgroundColor(Color.rgb(0, 128, 0));
        }
        else {
            mToast.setText(message);
        }
        mToast.show();

    }

    /**
     * figures out what to do when a square is touched: create a move, highlight a square, try to perform am
     * move etc.
     * @param touchedSquareIndex
     */
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
            updateDisplay();
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


                updateDisplay();
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
            updateDisplay();
            return;
        }
        if(firstSquareIndex == Constants.UNUSED_SQUARE //if this is the first square touched
                && board.getSquares()[touchedSquareIndex].getPiece() != null //and there is a piece there
                && board.getSquares()[touchedSquareIndex].getPiece().getBelongsTo().getColor()==playerTurn) { //and the piece belongs to the player with control
            firstSquareIndex = touchedSquareIndex; //set the first square
            board.getSquares()[firstSquareIndex].setActive(true); //highlight the square
            updateDisplay();
            return;
        }
        else { //else its the second square touched

            secondSquareIndex = touchedSquareIndex; //set the second square

            if(firstSquareIndex == Constants.UNUSED_SQUARE) { //if the first square is not set
                //displayMessage("You may only select your own pieces.");
                updateDisplay();
                secondSquareIndex = Constants.UNUSED_SQUARE;
                return;
            }

            if(firstSquareIndex == secondSquareIndex) { //if the first and 2nd are the same
                board.getSquares()[firstSquareIndex].setActive(false); //unhighlight
                updateDisplay();
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
            updateDisplay();
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
        updateDisplay();
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
     * function see if a move results in a follow up jump
     */
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

    /**
     * Checks to see if a proposed move is legal
     */
    public boolean checkIfLegalMove() {
        boolean legal = false;

        if(playerTurn == PlayerColor.BLACK) {
            for(Move m : board.getLegalMovesBlack()) {
                if (m.getStartSquareIndex() == move.getStartSquareIndex() &&
                        m.getTargetSquareIndex() == move.getTargetSquareIndex()) {
                    if(!board.getHasJumpBlack()) {
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

    /**
     * proposes a move, checks if its legal and if it is ask the board to perform it
     */
    public void performMove(){
        if(checkIfLegalMove()) {
            board.performMove(move);
            board.getSquares()[move.getStartSquareIndex()].setActive(false);

            if(board.convertToKing(move.getTargetSquareIndex())) {
                followUpJump = false;
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
            if(board.getHasJumpBlack() && playerTurn == PlayerColor.BLACK) {
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

    /**
     * start the next turn, if AI have the AI move, if player wait for a proposed move
     */
    public void startNextTurn() {

        if (playerTurn == PlayerColor.BLACK) {
            playerTurn = PlayerColor.RED;
            displayMessage(playerTwo.getName() + "'s turn to move.");
        } else {
            displayMessage(playerOne.getName() + "'s turn to move.");
            playerTurn = PlayerColor.BLACK;
        }

        // make sure no one has won yet
        if(!checkWinConditions()) {
            move = new Move(-1, -1);

            if (gameType == GameType.AI && playerTurn == PlayerColor.RED) {
                //moveAI();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    moveAI(); } }, 1000);
            }
        }

    }

    /**
     * function to see if a player has lost at the start of their turn
     * If they have no pieces or legal moves and its their turn: they lose
     */
    public boolean checkWinConditions() {
        if (playerTurn == PlayerColor.BLACK && (board.getNumBlackPieces()<=0 || board.getLegalMovesBlack().isEmpty())){
           // displayMessage(playerTwo.getName() + " is the winner!");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Game Over");
            builder.setMessage(playerTwo.getName() + " is the winner!");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Activity act = (Activity) context;
                    act.setContentView(R.layout.activity_main);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return true;
        }
        else if (playerTurn == PlayerColor.RED && (board.getNumRedPieces()<=0 || board.getLegalMovesRed().isEmpty())){
           // displayMessage(playerOne.getName() + " is the winner!");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Game over");
            builder.setMessage(playerOne.getName() + " is the winner!");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Activity act = (Activity) context;
                    act.setContentView(R.layout.activity_main);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return false;
    }

    /**
     * Logic for AI movements; rule based
     * AI will jump, or try to save its pieces if in danger, or try to king a piece, or try to
     * not move to a dangerous square. Otherwise it will choose a random move
     */
    public void moveAI() {
        ArrayList<Move> allMoves = board.getLegalMovesRed();
        ArrayList<Move> allJumps = new ArrayList<Move>();
        ArrayList<Move> enemyMoves = board.getLegalMovesBlack();
        ArrayList<Move> enemyJumps = new ArrayList<Move>();
        boolean canKing = false;
        Move moveToKing = null;

        for(Move m : allMoves) {
            int targetSquareIndex = m.getTargetSquareIndex();
            if(targetSquareIndex == 1 || targetSquareIndex == 2 || targetSquareIndex == 3 || targetSquareIndex == 4){ //AI  can king
                canKing = true;
                moveToKing = m;
                if(board.getSquares()[moveToKing.getStartSquareIndex()].getPiece().getPieceType() == PieceType.KING) {
                    canKing = false;
                }
            }
            if(m.getMoveType() == MoveType.JUMP) {
                allJumps.add(m);
            }
        }
        for(Move m : enemyMoves) {
            if (m.getMoveType() == MoveType.JUMP) {
                enemyJumps.add(m);
            }
        }
        if(allMoves.isEmpty()){
            return;
        }
        Random rand = new Random();
        if(allJumps.isEmpty()) { //if AI doesn't have a jump
            if(!enemyJumps.isEmpty()){ //if enemy has a jump
                int index = rand.nextInt(enemyJumps.size());
                Move m = enemyJumps.get(index);
                int i = m.getTargetSquareIndex();
                int j = m.getIndexOfJumpedSquare();
                for(Move aiMove : allMoves) {
                    if(aiMove.getTargetSquareIndex() == i) { //if AI can block the jump
                        move = aiMove;
                    }
                    else if (aiMove.getStartSquareIndex() == j) { //if AI can move out of the jump
                        move = aiMove;
                    }
                }
            }
            else if (canKing) { //if AI can king do it
                move = moveToKing;
            }
            else { //default to a move
                ArrayList<Move> goodMoves = new ArrayList<Move>();
                boolean noGoodMove = true;
                for(Move AIm : allMoves) { //check to see if we are potentially moving into a jump
                    Square[] AISquares = new Square[board.getSquares().length];
                    for (int i = 0; i < AISquares.length; i++) {
                        Square s = board.getSquares()[i];
                        if (s != null) {
                            AISquares[i] = new Square(s.getRect(), s.getPiece());
                        }
                    }
                    CurrentBoard AIBoard = new CurrentBoard(AISquares); //AI has own board
                    AIBoard.performMove(AIm);
                    ArrayList<Move> futureEnemyMoves = AIBoard.getLegalMovesBlack();
                    boolean blackHasFutureJump = false;
                    for(Move m : futureEnemyMoves) {
                        if(m.getMoveType() == MoveType.JUMP) {
                            blackHasFutureJump = true;
                        }
                    }
                    if(!blackHasFutureJump){
                        goodMoves.add(AIm);
                    }
                }
                if(goodMoves.isEmpty()) { //we didn't find a good move so do a random one
                    int index = rand.nextInt(allMoves.size());
                    move = allMoves.get(index);
                }
                else{
                    int index = rand.nextInt(goodMoves.size());
                    move = goodMoves.get(index);
                }
            }
        }
        else { //AI must jump
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
        if(move.getStartSquareIndex() == -1) {
            int index = rand.nextInt(allMoves.size());
            move = allMoves.get(index);
        }
        System.out.println("AI MOVE " + move.getStartSquareIndex() + " " + move.getTargetSquareIndex());
        performMove();
        updateDisplay();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(followUpJump) {
                    moveAI(); } } }, 1000);

    }
}
