package com.example.nir30.minesweeper;

import android.graphics.Point;

public class Cell extends  Point{
    private  int  minesAround;
    private boolean isPressed, isMine, userSetFlag;

    public Cell(int i, int j) {
        super(i ,j);
        isPressed = false;
        isMine = false;
        userSetFlag = false;
        minesAround = 0;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void addOneMineAround(){
        this.minesAround++;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }
}
