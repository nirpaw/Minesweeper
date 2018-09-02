package com.example.nir30.minesweeper;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {
    private int numOfRows;
    private int numOfCols;
    private int numOfBombs;
    private Cell[][] boardMatrix;
    ArrayList<Point> minesLocation; // Locations of all the mines (i,j)

    public Board(int numOfRows, int mumOfCols, int numOfBombs) {
        this.numOfRows = numOfRows;
        this.numOfCols = mumOfCols;
        this.numOfBombs = numOfBombs;
        initBoard();
        Log.d("ctr", "inited");
        randomlyDispersMines();
        Log.d("ctr", "randomlyDispersMines");
        setValueForCells();
        Log.d("ctr", "setValueForCells");
        String matrixStr = printBoard();
        Log.d("ctr", matrixStr);
    }

    private void  setValueForCells(){
        for (Point mine:this.minesLocation) {
            for(int i = mine.x - 1 ; i <= mine.x + 1 ; i++){
                for (int j = mine.y - 1 ; j <= mine.y + 1 ; j++ ){
                    if(!(i < 0 || i >= boardMatrix.length || j < 0 || j >= boardMatrix[0].length))
                    {
                        boardMatrix[i][j].addOneMineAround();
                    }
                }
            }
        }
    }

    private String printBoard (){
        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < boardMatrix.length ; i++) {
            for (int j = 0 ; j < boardMatrix[0].length ; j++){
                if (this.boardMatrix[i][j].isMine()){
                    sb.append("X|");
                }else {
                    sb.append(boardMatrix[i][j].getMinesAround()+"|");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void randomlyDispersMines()
    {
        Random rnd = new Random();
        minesLocation = new ArrayList<>();
        int mineToDispers = numOfBombs;
        boolean mineAlreadyExist = false;
        while(mineToDispers > 0) {
            Point newMine = new Point(rnd.nextInt(numOfRows), rnd.nextInt(numOfCols));
            for (Point mine : minesLocation) {
                if (mine.equals(newMine)) {
                    mineAlreadyExist = true;
                }
            }

            if (!mineAlreadyExist){
                minesLocation.add(newMine);
                this.boardMatrix[newMine.x][newMine.y].setMine(true);
                mineToDispers--;
            }
            mineAlreadyExist = false;
        }
    }

    private void initBoard(){
        this.boardMatrix =  new Cell[numOfRows][numOfCols];
        for(int i = 0 ; i < boardMatrix.length ; i++) {
            for (int j = 0 ; j < boardMatrix[0].length ; j++){
                this.boardMatrix[i][j] = new Cell(i,j);
            }
        }
    }
}
