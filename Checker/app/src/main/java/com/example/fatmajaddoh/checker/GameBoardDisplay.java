package com.example.fatmajaddoh.checker;

/**
 * Created by fatmajaddoh on 2/21/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GameBoardDisplay extends BaseAdapter {

    Context mContext;
    private int[] boardIds = { //Array of black and white board squares
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


    public GameBoardDisplay(Context c) {

        mContext = c;}

    @Override
    public int getCount() {
        return boardIds.length;}

    @Override
    public Object getItem(int arg0) {
        return null;}

    @Override
    public long getItemId(int arg0) {
        return 0;}

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
            squareView.setPadding(0, 0, 0, 0);
            squareView.setImageResource(boardIds[position]);
            //Placing Pieces in The Board
            if (boardIds[position] == R.drawable.darksquare) {
                if (position <= 23) {
                    final ImageView pieceView =
                            (ImageView) squareContainerView.findViewById(R.id.piece);

                    pieceView.setImageResource(R.drawable.blackpicese);
                    pieceView.setTag(position);
                } else if (position > 39) {
                    final ImageView pieceView =
                            (ImageView) squareContainerView.findViewById(R.id.piece);
                    pieceView.setImageResource(R.drawable.redpicese);
                    pieceView.setTag(position);
                }} }


        return squareContainerView;


    }



}
