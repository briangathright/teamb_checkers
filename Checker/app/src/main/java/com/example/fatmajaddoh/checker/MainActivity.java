package com.example.fatmajaddoh.checker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.content.Context;
import android.content.Intent;


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




           int cellWidth  =60;
           int cellHeigth  =60;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int Width = displaymetrics.widthPixels;
        int rows= height/cellHeigth;
        int columns = Width/cellWidth;

       LinearLayout celllayout=(LinearLayout)findViewById(R.id.cellLayout);

        Board [][] cellViews= new Board [rows][rows];
        for(int row =0;row<8;row++){

           LinearLayout rowLinearLayout=new LinearLayout(getApplicationContext());
            rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            Board[] rowArray=new Board[columns];
          for(int col=0;col<8;col++){

              Board button=new Board(this,row,col);
              button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                      TableRow.LayoutParams.MATCH_PARENT,10.0f));

              rowLinearLayout.addView(button);

              rowArray[col]=button;

              //button.(getResources().getColor(R.color.green));


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
