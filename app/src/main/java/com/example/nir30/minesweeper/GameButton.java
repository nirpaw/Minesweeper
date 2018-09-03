package com.example.nir30.minesweeper;

import android.content.Context;
import android.graphics.Point;
import android.widget.Button;

public class GameButton extends Button{
    int i, j ;
    public GameButton(Context context, int i , int j ){
        super(context);
        this.i = i;
        this.j = j;

    }
}
