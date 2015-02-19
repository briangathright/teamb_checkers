package com.example.fatmajaddoh.checker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;



public class MainActivity extends Activity {

    private static final int num_row=8;
    private static final int num_col=8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateButtons();
    }






    private void populateButtons() {

       int cellWidth  =100;
       int cellHeigth  =100;
       //get screen size
        Display display =  getWindowManager().getDefaultDisplay();
        Point size =new Point();
        display.getSize(size);
        int height = size.y;
        int Width = size.x;
        int rows= height/cellHeigth;
       int columns = Width/cellWidth;

       LinearLayout celllayout=(LinearLayout)findViewById(R.id.cellLayout);

        Board [][] cellViews= new Board [rows][rows];
        for(int row =0;row<num_row;row++){

        LinearLayout rowLinearLayout=new LinearLayout(getApplicationContext());
            rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


         Board[] rowArray=new Board[columns];
          for(int col=0;col<num_col;col++){
            Board button=new Board(this,row,col);
             button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,70.0f));
             //Cell coloring
              if(row%2==0 )
              if (col%2==0 )
               button.setBackgroundColor(getResources().getColor(R.color.Black));
               else  button.setBackgroundColor(getResources().getColor(R.color.white));
            else
             if (col%2!=0)
                 button.setBackgroundColor(getResources().getColor(R.color.Black));
              else  button.setBackgroundColor(getResources().getColor(R.color.white));

              rowLinearLayout.addView(button);
              rowArray[col]=button;
          // Placing black pieces in the board
              if (row==0||row==2)
                 if (col%2==0 )
              button.setBackgroundResource(R.drawable.checker);
             if(row==1)
                if (! (col%2==0 ))
                      button.setBackgroundResource(R.drawable.checker);


            // Placing red pieces in the board
              if (row==5||row==7)
                  if (col%2!=0)
                      button.setBackgroundResource(R.drawable.redpicese);

              if(row==6)
                  if (col%2==0 )
                      button.setBackgroundResource(R.drawable.redpicese);



          }
            cellViews[row]=rowArray;
            celllayout.addView(rowLinearLayout);
           }}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
