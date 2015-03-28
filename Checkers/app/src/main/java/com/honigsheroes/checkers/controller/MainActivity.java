package com.honigsheroes.checkers.controller;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.honigsheroes.checkers.R;
import com.honigsheroes.checkers.model.CheckersGame;
import com.honigsheroes.checkers.Constants.*;
import com.honigsheroes.checkers.model.CurrentBoard;
import com.honigsheroes.checkers.model.Piece;
import com.honigsheroes.checkers.model.Player;
import com.honigsheroes.checkers.model.Square;
import com.honigsheroes.checkers.view.GameBoardDisplay;

/** Main activity for Honig's Heroes' Checkers */
public class MainActivity extends Activity implements CheckersSystem{

    protected Square[] squares = new Square[33];
    protected StateOfGame stateOfGame; //status of currentGame
    protected CheckersGame currentGame; //the model
    protected GameBoardDisplay boardDisplay;
    protected Player playerOne;
    protected Player playerTwo;
    protected GameType gameType; //TODO: Fatma set this accordingly and pass it to game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playerOne = new Player(PlayerColor.BLACK);
        playerTwo = new Player(PlayerColor.RED);


        stateOfGame = StateOfGame.READY; //so we can start games.
        setContentView(R.layout.activity_main); //main menu layout
    }
    public void createSquares(){
        // get the width and height of the screen. Create initial set of squares for board
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        int squareWidth = width/10;
        int squareHeight = width/10;

        int leftx = squareWidth * 2;
        int rightx = squareWidth * 3;
        int topy = squareHeight;
        int boty = squareHeight * 2;
        int index = 1;
        squares[0]= new Square(new Rect(squareWidth*9,0,squareWidth*10,squareHeight),null);
        for (int row = 0; row < 8; row++) {
            if(row%2 == 0) {
                leftx = squareWidth * 2;
                rightx = squareWidth * 3;
            }
            else {
                leftx = squareWidth;
                rightx = squareWidth * 2;
            }
            for (int column = 0; column < 4; column++) {
                if(row < 3) {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerOne, index, PieceType.MAN));
                }
                else if (row > 4) {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerTwo,index, PieceType.MAN));
                }
                else {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), null);
                }
                leftx += squareWidth * 2;
                rightx += squareWidth * 2;
                index++;
            }
            topy = boty;
            boty += squareHeight;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //startGame(); //currently hard coded to just start a game, which just displays board
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /** Starts a game instance assuming there isn't one already.*/
    @Override
    public boolean startGame() {
        if (stateOfGame.equals(StateOfGame.READY)) {
            stateOfGame = StateOfGame.PLAYING;

            //other initialization stuff

            createSquares();
            boardDisplay = new GameBoardDisplay(squares, this, playerOne.getName(), playerTwo.getName());
            CurrentBoard cboard = new CurrentBoard(squares, boardDisplay);
            currentGame = new CheckersGame(cboard, playerOne, playerTwo, GameType.HUMAN); //hardcoded rightnow to start human game

            setContentView(boardDisplay); //displays the board
            return true;
        }
        return false;
    }

    /** Passes touch event coordinates to the currentGame, if there is one*/
    public boolean onTouchEvent(MotionEvent e) {
        if(stateOfGame.equals(StateOfGame.PLAYING) && e.getAction()==MotionEvent.ACTION_UP) {
            int touchedSquareIndex = boardDisplay.getTouchedSquare();
            if (touchedSquareIndex==0){
                quitGame();
            }
            currentGame.onClick(touchedSquareIndex); //pass the x and y coordinates to game to see which square was clicked etc.
            //we can use dimensions of rects in 'squares' to figure out which square was touched.
            System.out.println("*******"+touchedSquareIndex);
            return true;
        }
        return false;
    }

    private boolean quitGame() {
        if (stateOfGame.equals(StateOfGame.PLAYING)) {
            stateOfGame = StateOfGame.READY;
            gameType = null; //or whatever we want to reset it
            setContentView(R.layout.activity_main);
            return true;
        }
        return false;
    }


    /**
     * TODO: Fatma
     * TODO: Make this function go to the new layout (you make) that has the options for which type of game
     * TODO: Make the new layout match
     */
    public void onClickStartButton(View view) {
        setContentView(R.layout.activity_nameselect);
    }

    /**
     * TODO: Fatma
     * TODO: Make a layout that has the two options AI or Human
     * TODO: When the AI button is clicked, create the game and set the gametype accurately
     */
    public void onClickAIGameButton(View view) {

    }

    /**
     * TODO: Fatma
     * TODO: Make a layout that has the two options AI or Human
     * TODO: When the human button is clicked, create the game and set the gametype accurately
     */

    public void onClickHumanGameButton(View view) {

    }

    /**
     * TODO: Fatma
     * TODO: Edit this function so that if its an AI game we only prompt for player 1's name
     */
    public void onClickContinueButton(View view) {
        EditText player1ET = (EditText) findViewById(R.id.player1EditText);
        EditText player2ET = (EditText) findViewById(R.id.player2EditText);

        String playerOneName = player1ET.getText().toString();
        String playerTwoName = player2ET.getText().toString();

        if(playerOneName.equals("")) {
            playerOneName = "Player 1";
        }
        if(playerTwoName.equals("")) {
            playerTwoName = "Player 2";
        }

        playerOne.setName(playerOneName);
        playerTwo.setName(playerTwoName);

        startGame();
    }
}
