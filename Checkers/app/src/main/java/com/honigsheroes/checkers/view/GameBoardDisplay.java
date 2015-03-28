package com.honigsheroes.checkers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.model.CheckersGame;
import com.honigsheroes.checkers.model.Move;
import com.honigsheroes.checkers.model.Player;
import com.honigsheroes.checkers.model.Square;

/**
 * This class extends view and is responsible for drawing the board.
 * The controller informs the model when things change and then the model subscribes
 * to the view so it can tell it to update.
 */
public class GameBoardDisplay extends View implements GameBoardDisplayListener {

    protected Square[] squares;
    protected int touchedSquareIndex;
    private boolean boardDrawn = false;
    private Paint paint = new Paint();
    private Canvas canvas;
    private String playerOneName;
    private String playerTwoName;

    public GameBoardDisplay(Square[] squares, Context context, String playerOneName, String playerTwoName) {
        super(context);
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;

        this.squares = squares;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        if (!boardDrawn) { // so we only draw the board (background) once
            drawBoard(canvas);
            drawPieces(canvas);
        } else {
            drawPieces(canvas); //pieces are redrawn every time a move is made.
        }
    }

    /**
     * TODO: Chris
     * TODO: Draw kings differently
     * TODO: Draw black pieces so they are more visible (white outline or something)
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 1; i < squares.length; i++) {
            if (squares[i].getPiece() != null) {

                if (squares[i].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.BLACK)) {
                    paint.setColor(Color.DKGRAY);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width() / 3, paint);
                } else if (squares[i].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.RED)) {
                    paint.setColor(Color.RED);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width() / 3, paint);
                }
            }
        }
    }


    /**
     * TODO: Chris
     * TODO: Change Background color, either to match the main menu's or not, just make it brighter / not the same color as black pieces
     * TODO: Change name color's if need be with new background
     */
    public void drawBoard(Canvas canvas) {
        int squareWidth = getWidth() / 10;
        int squareHeight = getHeight() / 10;


        paint.setColor(Color.DKGRAY); //this is the paint used for background
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(Color.RED);
        canvas.drawRect(squareWidth, squares[1].getRect().top, squareWidth * 9, squares[32].getRect().bottom, paint);

        paint.setColor(Color.BLACK);

        for (int i = 1; i < squares.length; i++) {

            if (squares[i].getActive()) {
                paint.setColor(Color.YELLOW);
            }
            else{
                paint.setColor(Color.BLACK);
            }
            canvas.drawRect(squares[i].getRect(), paint);
        }
        paint.setColor(Color.GREEN);
        paint.setTextSize(squares[0].getRect().height()/2);
        paint.setTextAlign(Paint.Align.CENTER);
        Rect r = new Rect();
        paint.getTextBounds(playerOneName,0,playerOneName.length(),r);
        canvas.drawText(playerOneName, squares[2].getRect().right , squares[1].getRect().height()/2 + r.height()/2, paint);
        r = new Rect();
        paint.getTextBounds(playerTwoName,0,playerTwoName.length(),r);
        canvas.drawText(playerTwoName, squares[2].getRect().right , squares[1].getRect().height()*9+ squares[1].getRect().height()/2 + r.height()/2, paint);
        paint.setColor(Color.CYAN);
        canvas.drawRect(squares[0].getRect(), paint);
        paint.setColor(Color.RED);


        paint.getTextBounds("X",0, "X".length(),r);
        canvas.drawText("X",squares[0].getRect().centerX(),squares[0].getRect().height()/2 +r.height()/2,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchedSquareIndex = findSquareIndex(event);
        //invalidate();
        return false;
    }

    public int findSquareIndex(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        for (int i = 0; i < squares.length; i++) {
            Rect rect = squares[i].getRect();
            if (rect.contains(x, y)) {
                return i;
            }
        }
        return Constants.UNUSED_SQUARE;
    }

    public int getTouchedSquare() {
        return touchedSquareIndex;
    }

    /**
     * whenever the view needs to update the CurrentBoard should call listener.update() which
     * will cause the view to invalidate itself. invalidate() tells the view to redraw itself.
     */
    @Override
    public void update() {

        touchedSquareIndex=Constants.UNUSED_SQUARE;

        invalidate();

    }


}
