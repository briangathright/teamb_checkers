package com.honigsheroes.checkers.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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

    private Square[] squares = new Square[33];
    private StateOfGame stateOfGame; //status of currentGame
    private CheckersGame currentGame; //the model
    private GameBoardDisplay boardDisplay;
    private Player playerOne;
    private Player playerTwo;
    private GameType gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playerOne = new Player(PlayerColor.BLACK);
        playerTwo = new Player(PlayerColor.RED);


        stateOfGame = StateOfGame.READY; //so we can start games.
        setContentView(R.layout.activity_main); //main menu layout
    }

    /**
     * This method creates the Checkers Board
    */

    private void createSquares(){
        // get the width and height of the screen. Create initial set of squares for board

        Display display =  getWindowManager().getDefaultDisplay();

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
        int index = 32;
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
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerTwo, PieceType.MAN));
                }
                else if (row > 4) {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerOne, PieceType.MAN));
                }
                else {
                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), null);
                }
                leftx += squareWidth * 2;
                rightx += squareWidth * 2;
                index--;
            }
            topy = boty;
            boty += squareHeight;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /** Starts a game instance assuming there isn't one already.
     * This function returns the start of the game if its true,
     * and false if there is some error or issue
     *
     * */
    @Override
    public boolean startGame() {
        if (stateOfGame.equals(StateOfGame.READY)) {
            stateOfGame = StateOfGame.PLAYING;

            //other initialization stuff


            createSquares();
            boardDisplay = new GameBoardDisplay(squares, this, playerOne.getName(), playerTwo.getName());
            CurrentBoard cboard = new CurrentBoard(squares);
            currentGame = new CheckersGame(this, boardDisplay, cboard, playerOne, playerTwo, gameType);
            setContentView(boardDisplay); //displays the board
            Drawable d = getResources().getDrawable(R.drawable.background16);
            boardDisplay.setBackground(d);
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
            System.out.println("*******" + touchedSquareIndex);
            return true;
        }
        return false;
    }

    //function to prompt confirmation to quit game
    private void quitGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Game?");
        builder.setMessage("Are you sure you want to quit the game and return to the " +
                "main menu?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (stateOfGame.equals(StateOfGame.PLAYING)) {
                    stateOfGame = StateOfGame.READY;
                    gameType = null; //or whatever we want to reset it
                    setContentView(R.layout.activity_main);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



// this method implements the start button so that when it this button is clicked
// it goes to the page where the player types are to be chosen
    public void onClickStartButton(View view) {
        setContentView(R.layout.players_types);

    }

//this method implements the AI option of the player type to be played against the player

    public void onClickAIGameButton(View view) {
        gameType =GameType.AI;
        setContentView(R.layout.select_names);

    }
//this method implements the player type of two humans playing against each other

    public void onClickHumanGameButton(View view) {
        gameType =GameType.HUMAN;
        setContentView(R.layout.activity_nameselect);

    }

//this function implements the Continue button where the player names are entered 
    public void onClickContinueButton(View view) {


        if (gameType ==GameType.HUMAN){
        EditText player1ET = (EditText) findViewById(R.id.player1EditText);
        EditText player2ET = (EditText) findViewById(R.id.player2EditText);

        String playerOneName = player1ET.getText().toString();
        String playerTwoName = player2ET.getText().toString();

        if(playerOneName.equals(getString(R.string.EmptyString))) {
            playerOneName = getString(R.string.Player1Name);
        }
        if(playerTwoName.equals(getString(R.string.EmptyString))) {
            playerTwoName = getString(R.string.Player2Name);
        }

        playerOne.setName(playerOneName);
        playerTwo.setName(playerTwoName);

        hideKeyboard(this);
        startGame();}

        else
        {EditText player1ET = (EditText) findViewById(R.id.player1EditText);

            String playerOneName = player1ET.getText().toString();

            if(playerOneName.equals(getString(R.string.EmptyString))) {
                playerOneName = getString(R.string.Player1Name);
            }

            playerOne.setName(playerOneName);
            playerTwo.setName(getString(R.string.ComputerPlayerName));

            hideKeyboard(this);
            startGame();



        }




    }
    private static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
