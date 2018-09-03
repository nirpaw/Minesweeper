package com.example.nir30.minesweeper;

import android.content.Context;
import android.graphics.Point;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameButton extends Button{
    int i, j ;
    boolean userFlag; //user set as flag
    public GameButton(Context context, int i , int j ){
        super(context);
        this.i = i;
        this.j = j;
    }

    public boolean isUserFlag() {
        return userFlag;
    }

    public void setUserFlag(boolean userFlag) {
        this.userFlag = userFlag;
    }
}
