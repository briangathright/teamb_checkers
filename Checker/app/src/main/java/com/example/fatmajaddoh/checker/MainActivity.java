package com.example.fatmajaddoh.checker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.ClipData;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;



public class MainActivity extends Activity {
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView celllayout = (GridView) findViewById(R.id.CHECKERBOARD);

        celllayout.setAdapter(new SquareAdapter(this));


    }


    public class SquareAdapter extends BaseAdapter {

        Context mContext;

        private int[] boardIds = {
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
                R.drawable.darksquare, R.drawable.lightsquare, R.drawable.darksquare, R.drawable.lightsquare,
        };


        public SquareAdapter(Context c) {
            mContext = c;


        }

        @Override
        public int getCount() {
            return boardIds.length;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View squareContainerView = convertView;

            if (convertView == null) {
                //Inflate the layout
                final LayoutInflater layoutInflater = (LayoutInflater)
                        this.mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
                squareContainerView =
                        layoutInflater.inflate(R.layout.square, null);

                // Background
                final ImageView squareView =
                        (ImageView) squareContainerView.findViewById(R.id.square_background);
                squareView.setOnDragListener(new MyDragListener());
                squareView.setPadding(-20, -20, -20, -20);
                squareView.setImageResource(boardIds[position]);

                if (boardIds[position] == R.drawable.darksquare) {
                    if (position <= 23) {
                        final ImageView pieceView =
                                (ImageView) squareContainerView.findViewById(R.id.piece);
                        pieceView.setOnTouchListener(new MyTouchListener());
                        pieceView.setImageResource(R.drawable.checker);
                        pieceView.setTag(position);
                    } else if (position > 39) {
                        final ImageView pieceView =
                                (ImageView) squareContainerView.findViewById(R.id.piece);
                        pieceView.setOnTouchListener(new MyTouchListener());
                        pieceView.setImageResource(R.drawable.redpicese);
                        pieceView.setTag(position);
                    }

                }


            }


            return squareContainerView;
        }
    }


    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    class MyDragListener implements View.OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            event.getAction();

            switch (event.getAction()) {
                  /*    case DragEvent.ACTION_DRAG_STARTED:
                       // layoutParams = (GridView.LayoutParams) v.getLayoutParams();

                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                      //  int x = (int) event.getX();
                        //int y = (int) event.getY();
                      //  break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                       // x = (int) event.getX();
                      //  y = (int) event.getY();
                      //  layoutParams.l = x;
                      //  layoutParams.topMargin = y;
                        v.setLayoutParams(layoutParams);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                      //  x = (int) event.getX();
                     //   y = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");

                        break;*/
                case DragEvent.ACTION_DROP:
                    Log.d(msg, "Dropped");
                        /*View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();

                        owner.removeView(view);

                        FrameLayout container = (FrameLayout) v;
                        container.addView(view);

                       view.setVisibility(View.VISIBLE);*/


                    break;
                default:
                    break;
            }
            return true;
        }
    }

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





