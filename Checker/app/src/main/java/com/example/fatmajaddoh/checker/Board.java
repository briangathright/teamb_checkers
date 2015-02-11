package com.example.fatmajaddoh.checker;
import android.widget.Button;
import android.content.Context;
/**
 * Created by fatmajaddoh on 2/7/15.
 */
public class Board extends Button{
int row;
int col;
public Board (Context context ,int rowVal,int colVal ){
     super(context);
     row=rowVal;
     col=colVal;
        }

    int  getRow(){
        return row;}

    int getCol(){
        return col;
        }
}
