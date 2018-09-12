package com.example.nir30.minesweeper;

import android.content.Context;

import com.example.nir30.minesweeper.grid.Cell;

public class GameEngine {

    private static GameEngine instance;
    private Context context; // to conect to vies like "score textview"

    CellGenerator[][] gameBoard;

    Cell[][] MinesweeperGrid = new Cell[numOfRows][numOfCols];

    public static final int numOfMines = 5;
    public static final int numOfRows = 5;
    public static final int numOfCols = 10;

    public static int getSize () {
        return numOfRows * numOfCols;
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    private GameEngine() {

    }

    public void creatBoard(Context context) {
        this.context = context;
        // Create the board and store it
        BoardGenerator gameBoardGenerator = new BoardGenerator(numOfRows, numOfCols,  numOfMines);
        gameBoard = gameBoardGenerator.getBoardMatrix();
        setGrid(context, gameBoard);
    }

    public Cell getCellAt(int position) {
        int x = position % numOfRows;
        int y = position / numOfRows;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt( int x , int y ){
        return MinesweeperGrid[x][y];
    }

    private  void  setGrid(final Context context, final  CellGenerator [][] grid){
        for(int i = 0 ; i < numOfRows ; i++){
            for(int j = 0 ; j < numOfCols ; j++){
                if(MinesweeperGrid[i][j] == null){
                    MinesweeperGrid[i][j] = new Cell(context, i, j);
                }
                MinesweeperGrid[i][j].setValue(gameBoard[i][j].getMinesAround());
                MinesweeperGrid[i][j].invalidate();
            }
        }
    }

    public void  click (int Xpos, int Ypos){
        if(Xpos >= 0 && Ypos >= 0 && Xpos < numOfRows && Ypos < numOfCols && !getCellAt(Xpos, Ypos).isClicked()){
            getCellAt(Xpos, Ypos).setClicked();
            // check if neibers are 0`S
            if (getCellAt(Xpos, Ypos).getValue() == 0 ) {
                for(int i = -1 ; i <= 1 ; i++){
                    for(int j = -1 ; j < 1 ;j++){
                        if(i != j){
                            click(Xpos + i , Ypos + j);
                        }
                    }
                }
            }
            if(getCellAt(Xpos, Ypos).isMine()){
                onGameLost();
            }
        }
//        checkEnd();
    }

    public void flag(int x , int y){
        boolean isFlagged = getCellAt(x, y).isFlaged();
        getCellAt(x, y ).setFlaged(!isFlagged);
        getCellAt(x , y).invalidate();
    }

    private void onGameLost(){
        // lost game
    }
}