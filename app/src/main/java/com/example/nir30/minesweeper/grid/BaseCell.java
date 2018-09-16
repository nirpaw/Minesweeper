package com.example.nir30.minesweeper.grid;

import android.content.Context;
import android.view.View;

import com.example.nir30.minesweeper.SessionManager;

public class BaseCell extends View{
    private int value;
    private boolean isMine;
    private boolean isRevealed;
    private boolean isClicked;
    private boolean isFlaged;
    private int x,y;
    private int position;


    public BaseCell(Context context) {
        super(context);

    }

    // getters n setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        isMine = false;
        isRevealed = false;
        isFlaged = false;

        if(value == -1){
            isMine = true;
        }
        this.value = value;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked() {
        this.isClicked = true;
        this.isRevealed = true;

        invalidate();
    }

    public boolean isFlaged() {
        return isFlaged;
    }

    public void setFlaged(boolean flaged) {
        isFlaged = flaged;
    }

    public int getXpos() {
        return x;
    }


    public int getYpos() {
        return y;
    }

    public int getPosition() {
        return position;
    }

//    public void setPosition(int position) {
//        this.position = position;
//        x = position / SessionManager.numOfRows;
//        y = position % SessionManager.numOfCols;
//        invalidate();
//    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.position = x * SessionManager.numOfCols + y ; // setting an individual code for each cell
        invalidate();

    }
}
