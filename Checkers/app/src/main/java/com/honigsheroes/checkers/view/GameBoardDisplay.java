package com.honigsheroes.checkers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.model.Move;
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

    public GameBoardDisplay(Square[] squares, Context context) {
        super(context);
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


    public void drawBoard(Canvas canvas) {
        int squareWidth = getWidth() / 10;
        int squareHeight = getHeight() / 10;

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(Color.RED);
        canvas.drawRect(squareWidth, squares[1].getRect().top, squareWidth * 9, squares[32].getRect().bottom, paint);

        paint.setColor(Color.BLACK);
        for (int i = 1; i < squares.length; i++) {
            canvas.drawRect(squares[i].getRect(), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchedSquareIndex = findSquareIndex(event);
        return false;
    }

    public int findSquareIndex(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        for (int i = 1; i < squares.length; i++) {
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
        invalidate();
    }

    @Override
    public void testUpdate(Move move) {
        this.updatePieces(move);
    }

    private void updatePieces(Move move) {
        Rect rect = squares[move.getStartSquareIndex()].getRect();
        paint.setColor(Color.DKGRAY);
//        canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom, paint);
        try {
            if (squares[move.getStartSquareIndex()].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.BLACK)) {
                paint.setColor(Color.DKGRAY);
                canvas.drawCircle(squares[move.getTargetSquareIndex()].getRect().exactCenterX(), squares[move.getTargetSquareIndex()].getRect().exactCenterY(), squares[move.getTargetSquareIndex()].getRect().width() / 3, paint);
            } else if (squares[move.getStartSquareIndex()].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.RED)) {
                paint.setColor(Color.RED);
                canvas.drawCircle(squares[move.getTargetSquareIndex()].getRect().exactCenterX(), squares[move.getTargetSquareIndex()].getRect().exactCenterY(), squares[move.getTargetSquareIndex()].getRect().width() / 3, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        update();
    }

}
