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
import com.honigsheroes.checkers.model.Player;
import com.honigsheroes.checkers.model.Square;
import com.honigsheroes.checkers.view.GameBoardDisplay;

/** Main activity for Honig's Heroes' Checkers */
//TODO: Rakesh implement the start button and player name prompting.
public class MainActivity extends Activity implements CheckersSystem{

    protected Square[] squares = new Square[33];
    protected StateOfGame stateOfGame; //status of currentGame
    protected CheckersGame currentGame; //the model
    protected GameBoardDisplay boardDisplay;
    protected String playerOneName;
    protected String playerTwoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "BLACK");
                }
                else if (row > 4) {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "RED");
                }
                else {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "None");
                }
                leftx += squareWidth * 2;
                rightx += squareWidth * 2;
                index++;
            }
            topy = boty;
            boty += squareHeight;
        }

        stateOfGame = StateOfGame.READY; //so we can start games.
        setContentView(R.layout.activity_main); //main menu layout
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
            //TODO: create CheckerGame instance here, wire model to view

            boardDisplay = new GameBoardDisplay(squares, this);
            CurrentBoard cboard = new CurrentBoard(squares, boardDisplay);
            //Player playerOne = new Player(playerOneName, PlayerColor.BLACK);
            //Player playerTwo = new Player(playerTwoName, PlayerColor.RED);
            currentGame = new CheckersGame(cboard); //TODO: pass players to game

            setContentView(boardDisplay); //displays the board
            return true;
        }
        return false;
    }

    /** Passes touch event coordinates to the currentGame, if there is one*/
    //TODO: touching isn't very accurate atm, half to touch in top half of squares... look to fix
    public boolean onTouchEvent(MotionEvent e) {
        if(stateOfGame.equals(StateOfGame.PLAYING)) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            currentGame.onClick(x, y); //pass the x and y coordinates to game to see which square was clicked etc.
            //we can use dimensions of rects in 'squares' to figure out which square was touched.
            return true;
        }
        return false;
    }

    public void onClickStartButton(View view) {
        setContentView(R.layout.activity_nameselect);
    }

    public void onClickContinueButton(View view) {
        EditText player1ET = (EditText) findViewById(R.id.player1EditText);
        EditText player2ET = (EditText) findViewById(R.id.player2EditText);

        playerOneName = player1ET.getText().toString();
        playerTwoName = player2ET.getText().toString();

        if(playerOneName.equals("")) {
            playerOneName = "Player 1";
        }
        if(playerTwoName.equals("")) {
            playerTwoName = "Player 2";
        }

        startGame();
    }
}
