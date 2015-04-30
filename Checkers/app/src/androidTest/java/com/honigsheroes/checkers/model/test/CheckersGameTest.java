//package com.honigsheroes.checkers.model.test;
//
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.test.mock.MockApplication;
//import android.test.mock.MockContext;
//import android.view.Display;
//
//import com.honigsheroes.checkers.Constants;
//import com.honigsheroes.checkers.Constants.PlayerColor;
//import com.honigsheroes.checkers.controller.MainActivity;
//import com.honigsheroes.checkers.model.CheckersGame;
//import com.honigsheroes.checkers.model.CurrentBoard;
//import com.honigsheroes.checkers.model.Piece;
//import com.honigsheroes.checkers.model.Player;
//import com.honigsheroes.checkers.model.Square;
//import com.honigsheroes.checkers.view.GameBoardDisplay;
//
//import junit.framework.TestCase;
//
///**
// * Created by pulu on 4/23/15.
// */
//public class CheckersGameTest extends TestCase {
//
//    private PlayerColor playerTurn;
//    private CurrentBoard board;
//    private Square[] squares;
//    private Player playerOne;
//    private Player playerTwo;
//    private GameBoardDisplay boardDisplay;
//    private CheckersGame currentGame;
//    private Constants.GameType gameType;
//    private MockApplication activity;
//    private CheckersMockContext context;
//
//    @Override
//    protected void setUp() throws Exception {
//        playerTurn= PlayerColor.BLACK;
//        squares = new Square[33];
//        playerOne = new Player(PlayerColor.BLACK);
//        playerOne.setName("PlayerOne");
//        playerTwo = new Player(PlayerColor.RED);
//        playerTwo.setName("PlayerTwo");
//        createSquares();
//        board=new CurrentBoard(squares);
//        activity = new MockApplication();
//        context = new CheckersMockContext();
//        gameType = Constants.GameType.AI;
//        boardDisplay = new GameBoardDisplay(squares, context, playerOne.getName(), playerTwo.getName());
//        currentGame = new CheckersGame(context, boardDisplay, new CurrentBoard(squares), playerOne, playerTwo, gameType);
//    }
//
//    private void createSquares(){
//        Point size = new Point();
//
//        int width = size.x;
//        int height = size.y;
//        int squareWidth = width/10;
//        int squareHeight = width/10;
//
//        int leftx = squareWidth * 2;
//        int rightx = squareWidth * 3;
//        int topy = squareHeight;
//        int boty = squareHeight * 2;
//        int index = 32;
//        squares[0]= new Square(new Rect(squareWidth*9,0,squareWidth*10,squareHeight),null);
//        for (int row = 0; row < 8; row++) {
//            if(row%2 == 0) {
//                leftx = squareWidth * 2;
//                rightx = squareWidth * 3;
//            }
//            else {
//                leftx = squareWidth;
//                rightx = squareWidth * 2;
//            }
//            for (int column = 0; column < 4; column++) {
//                if(row < 3) {
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerTwo, Constants.PieceType.MAN));
//                }
//                else if (row > 4) {
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), new Piece(playerOne, Constants.PieceType.MAN));
//                }
//                else {
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), null);
//                }
//                leftx += squareWidth * 2;
//                rightx += squareWidth * 2;
//                index--;
//            }
//            topy = boty;
//            boty += squareHeight;
//        }
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
//
//    public void testCheckWinConditions(){
//        currentGame.checkWinConditions();
//    }
//
//    public void testAA(){
//        assertEquals(2,2);
//    }
//
//
//}
