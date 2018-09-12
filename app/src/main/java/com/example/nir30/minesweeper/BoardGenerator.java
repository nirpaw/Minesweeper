package com.example.nir30.minesweeper;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class BoardGenerator {
    private int numOfRows;
    private int numOfCols;
    private int numOfMines;
    private CellGenerator[][] boardMatrix;
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

    public CellGenerator[][] getBoardMatrix() {
        return boardMatrix;
    }

    private void setBoard(){
        initEmptyMatrix();
        randomlyDispersMines();
        setValueForCells();
        Log.d("ctr", printBoard());
        removeValueForMines();
    }

    private void removeValueForMines(){
        for (Point mine: minesLocation) {
            boardMatrix[mine.x][mine.y].setMinesAround(-1);
        }
    }
    private void  setValueForCells(){ // Runs on each mine neighbors and add 1 to his value(minesAround)
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

    private String printBoard (){ // for debug
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
                this.boardMatrix[newMine.x][newMine.y].setMine(true);
                this.boardMatrix[newMine.x][newMine.y].setMinesAround(-1); // no value needed
                mineToDispers--;
            }
            mineAlreadyExist = false;
        }
    }

    private void initEmptyMatrix(){
        this.boardMatrix =  new CellGenerator[numOfRows][numOfCols];
        for(int i = 0 ; i < boardMatrix.length ; i++) {
            for (int j = 0 ; j < boardMatrix[0].length ; j++){
                this.boardMatrix[i][j] = new CellGenerator(i,j);
            }
        }
    }
}
