package com.example.fatmajaddoh.checker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView cellLayout = (GridView) findViewById(R.id.CHECKERBOARD);
        cellLayout.setHorizontalSpacing(0);
        cellLayout.setAdapter(new GameBoardDisplay(this));
    }
}








