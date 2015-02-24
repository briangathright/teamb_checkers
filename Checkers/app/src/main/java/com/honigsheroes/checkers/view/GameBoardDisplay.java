package com.honigsheroes.checkers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.honigsheroes.checkers.model.CurrentBoard;
import com.honigsheroes.checkers.model.Square;

/**
 * This class extends view and is responsible for drawing the board.
 * The controller informs the model when things change and then the model subscribes
 * to the view so it can tell it to update.
 */
public class GameBoardDisplay extends View implements GameBoardDisplayListener {

    private boolean boardDrawn = false;

    private Paint paint = new Paint();
    protected Square[] squares;

    public GameBoardDisplay(Square[] squares, Context context) {
        super(context);
        this.squares = squares;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!boardDrawn) { // so we only draw the board (background) once
            drawBoard(canvas);
            drawPieces(canvas);
        }
        else {
            drawPieces(canvas); //pieces are redrawn every time a move is made.
        }
    }

    private void drawPieces(Canvas canvas) {
        for(int i = 1; i < squares.length; i++) {
            if(squares[i].owner.equals("BLACK")) {
                paint.setColor(Color.DKGRAY);
                canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width()/3, paint);
            }
            else if(squares[i].owner.equals("RED")) {
                paint.setColor(Color.RED);
                canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width()/3, paint);
            }
        }
    }

    public void drawBoard(Canvas canvas) {
        int squareWidth = getWidth()/10;
        int squareHeight = getHeight()/10;

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(Color.RED);
        canvas.drawRect(squareWidth, squares[1].getRect().top, squareWidth*9, squares[32].getRect().bottom, paint);

        paint.setColor(Color.BLACK);
        for(int i = 1; i < squares.length; i++) {
            canvas.drawRect(squares[i].getRect(), paint);
        }

//        // all of this could/should be pulled out to the controller / main activity.
//        int leftx = squareWidth * 2;
//        int rightx = squareWidth * 3;
//        int topy = squareHeight;
//        int boty = squareHeight * 2;
//        int index = 1;
//
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
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "BLACK");
//                }
//                else if (row > 4) {
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "RED");
//                }
//                else {
//                    squares[index] = new Square(new Rect(leftx, topy, rightx, boty), "None");
//                }
//                canvas.drawRect(squares[index].getRect(), paint);
//                leftx += squareWidth * 2;
//                rightx += squareWidth * 2;
//                index++;
//            }
//            topy = boty;
//            boty += squareHeight;
//        }
    }

    /**
     * whenever the view needs to update the CurrentBoard should call listener.update() which
     * will cause the view to invalidate itself. invalidate() tells the view to redraw itself.
     */
    @Override
    public void update() {
        invalidate();
    }
}
