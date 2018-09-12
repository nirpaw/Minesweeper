package com.example.nir30.minesweeper;


import java.util.*;
import android.graphics.*;


public class CellGenerator extends  Point{
    private  int  minesAround;
    private boolean isPressed, isMine, userSetFlag;

    public CellGenerator(int i, int j) {
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

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
