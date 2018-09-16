package com.example.nir30.minesweeper.Generator;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

//  -1 means a mine

public class BoardGenerator {

    private int numOfCols;
    private int numOfRows;
    private int numOfMines;
    private int[][] boardMatrix;
    private ArrayList<Point> minesLocation; // Locations of all the mines (i,j)

    public BoardGenerator(int numOfRows, int mumOfCols, int numOfMines) {
        this.numOfRows = numOfRows;
        this.numOfCols = mumOfCols;
        this.numOfMines = numOfMines;
        setBoard();
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    private void setBoard(){
        this.boardMatrix =  new int[numOfRows][numOfCols]; // initEmptyMatrix
        randomlyDispersMines();
        setValueForCells();
        Log.d("ctr", printBoard());
    }

    private void  setValueForCells(){ // Runs on each mine neighbors and add 1 to his value(minesAround)
        for (Point mine:this.minesLocation) {
            for(int i = mine.x - 1 ; i <= mine.x + 1 ; i++){
                for (int j = mine.y - 1 ; j <= mine.y + 1 ; j++ ){
                    if(!(i < 0 || i >= boardMatrix.length || j < 0 || j >= boardMatrix[0].length))
                    {
                        if(boardMatrix[i][j] != -1)
                        boardMatrix[i][j]++;
                    }
                }
            }
        }
    }

    private String printBoard (){ // for debug
        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < boardMatrix.length ; i++) {
            for (int j = 0 ; j < boardMatrix[0].length ; j++){
                if (this.boardMatrix[i][j] == -1){
                    sb.append("X|");
                }else {
                    sb.append(boardMatrix[i][j]+"|");
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
        int mineToDispers = numOfMines;
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
                this.boardMatrix[newMine.x][newMine.y] = -1;
                mineToDispers--;
            }
            mineAlreadyExist = false;
        }
    }
}
