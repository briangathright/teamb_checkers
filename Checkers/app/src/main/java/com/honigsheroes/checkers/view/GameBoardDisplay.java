package com.honigsheroes.checkers.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.R;
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
    private Context context;

    public GameBoardDisplay(Square[] squares, Context context, String playerOneName, String playerTwoName) {
        super(context);
        this.context = context;
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


    private void drawPieces(Canvas canvas) {
        paint.setTextSize(squares[0].getRect().height()/2);
        paint.setTextAlign(Paint.Align.CENTER);
        int redCount = 12;
        int blackCount = 12;
        for (int i = 1; i < squares.length; i++) {
            if (squares[i].getPiece() != null) {

                if (squares[i].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.BLACK)) {
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), (float) (squares[i].getRect().width() / 2.7), paint);
                    paint.setColor(Color.DKGRAY);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width() / 3, paint);
                    blackCount--;
                } else if (squares[i].getPiece().getBelongsTo().getColor().equals(Constants.PlayerColor.RED)) {
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), (float) (squares[i].getRect().width() / 2.7), paint);
                    paint.setColor(Color.RED);
                    canvas.drawCircle(squares[i].getRect().exactCenterX(), squares[i].getRect().exactCenterY(), squares[i].getRect().width() / 3, paint);
                    redCount--;
                }
                if (squares[i].getPiece().getPieceType().equals(Constants.PieceType.KING)) {
                    paint.setColor(Color.WHITE);
                    canvas.drawText("K",squares[i].getRect().exactCenterX(),squares[i].getRect().exactCenterY()+squares[i].getRect().height()/6,paint);
                }

            }
        }
        drawDead(blackCount,redCount);

    }
    public void drawDead(int blackCount, int redCount){
        paint.setTextSize((float)(squares[0].getRect().height()/2.2));

        float blackXcoord = (float)(squares[29].getRect().left+squares[29].getRect().width()/2);
        float redXcoord = (float)(squares[32].getRect().right+squares[32].getRect().width()/2);
        for (int i = 1; i <= blackCount; i++){
            paint.setColor(Color.WHITE);
            canvas.drawCircle(blackXcoord, (float) (squares[29].getRect().bottom+squares[29].getRect().height()*1.5),(float)(squares[29].getRect().width()/2.7), paint);
            paint.setColor(Color.DKGRAY);
            canvas.drawCircle(blackXcoord, (float) (squares[29].getRect().bottom+squares[29].getRect().height()*1.5),(float)(squares[29].getRect().width()/3), paint);
            if (i == blackCount) {

                paint.setColor(Color.WHITE);
                canvas.drawText(i+"",blackXcoord ,(float) (squares[29].getRect().bottom+squares[29].getRect().height()*1.65),paint);
            }
            blackXcoord += squares[29].getRect().width()/3.2;


        }
        for (int i = 1; i <= redCount; i++){
            paint.setColor(Color.WHITE);
            canvas.drawCircle(redXcoord, (float) (squares[32].getRect().bottom+squares[32].getRect().height()*1.5),(float)(squares[32].getRect().width()/2.7), paint);
            paint.setColor(Color.RED);
            canvas.drawCircle(redXcoord, (float) (squares[32].getRect().bottom+squares[32].getRect().height()*1.5),(float)(squares[32].getRect().width()/3), paint);
            if (i == redCount) {

                paint.setColor(Color.WHITE);
                canvas.drawText(i+"",redXcoord,(float) (squares[32].getRect().bottom+squares[32].getRect().height()*1.65),paint);
            }
            redXcoord -= squares [32].getRect().width()/3.2;


        }
    }


    public void drawBoard(Canvas canvas) {
        int squareWidth = getWidth() / 10;
        int squareHeight = getHeight() / 10;


        //paint.setColor(Color.rgb(139,16,19));//this is the paint used for background
        //canvas.drawRect(0, 0, getWidth(), getHeight(), paint);




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
        paint.setColor(Color.BLACK);
        paint.setTextSize(squares[0].getRect().height()/2);
        paint.setTextAlign(Paint.Align.CENTER);
        Rect r = new Rect();
        paint.getTextBounds(playerOneName,0,playerOneName.length(),r);
        canvas.drawText(playerOneName, squares[2].getRect().right , squares[1].getRect().height()/2 + r.height()/2, paint);
        r = new Rect();
        paint.getTextBounds(playerTwoName,0,playerTwoName.length(),r);
        canvas.drawText(playerTwoName, squares[2].getRect().right , squares[1].getRect().height()*9+ squares[1].getRect().height()/2 + r.height()/2, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(squares[0].getRect(), paint);
        paint.setColor(Color.WHITE);


        paint.getTextBounds("Quit",0, "Quit".length(),r);
        canvas.drawText("Quit",squares[0].getRect().centerX(),squares[0].getRect().height()/2 +r.height()/2,paint);

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
