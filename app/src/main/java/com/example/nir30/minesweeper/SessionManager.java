package com.example.nir30.minesweeper;

import android.content.Context;
import android.widget.Toast;

import com.example.nir30.minesweeper.Generator.BoardGenerator;
import com.example.nir30.minesweeper.grid.Cell;

public class SessionManager {

    private int totalFlagsCounter = 0;
    private int correctFlagsCounter = 0;
    private static SessionManager instance;
    private Context context;
    int[][] gameBoard; // generated logic board
    Cell[][] MinesweeperGrid = new Cell[numOfRows][numOfCols];

    public static final int numOfMines = 5;
    public static final int numOfRows = 9;
    public static final int numOfCols = 10;

    public static int getSize () {
        return numOfRows * numOfCols;
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager() {

    }

    public void creatBoard(Context context) {
        this.context = context;
        // Create the board and store it
        BoardGenerator gameBoardGenerator = new BoardGenerator(numOfRows, numOfCols,  numOfMines);
        gameBoard = gameBoardGenerator.getBoardMatrix();
        setGrid(context, gameBoard);
    }

    public Cell getCellAt(int position) {
        int x = position / numOfCols;
        int y = position % numOfCols  ;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt( int x , int y ){
        return MinesweeperGrid[x][y];
    }

    private  void  setGrid(final Context context, final  int [][] board){
        for(int i = 0 ; i < numOfRows ; i++){
            for(int j = 0 ; j < numOfCols ; j++){
                if(MinesweeperGrid[i][j] == null){
                    MinesweeperGrid[i][j] = new Cell(context, i, j);
                }
                MinesweeperGrid[i][j].setValue(board[i][j]);
                MinesweeperGrid[i][j].invalidate();
            }
        }
    }

    public void  click (int Xpos, int Ypos){

        if(Xpos >= 0 && Ypos >= 0 && Xpos < numOfRows && Ypos < numOfCols && !getCellAt(Xpos, Ypos).isClicked()){
            if(getCellAt(Xpos, Ypos).isFlaged()){
                return;
            }
            getCellAt(Xpos, Ypos).setClicked();
            // check if neibers are 0`S
            if (getCellAt(Xpos, Ypos).getValue() == 0 ) {
                for(int i = -1 ; i <= 1 ; i++){
                    for(int j = -1 ; j <= 1 ; j++){
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
        if(getCellAt(x,y).isClicked()){
            return;
        }
        boolean isFlagged = getCellAt(x, y).isFlaged();
        if(!isFlagged) {
            getCellAt(x, y).setFlaged(true);
            if(getCellAt(x, y ).isMine()){
                this.correctFlagsCounter++;
            }
            totalFlagsCounter++;
        }else {
            getCellAt(x,y).setFlaged(false);
            if (getCellAt(x,y).isMine()){
                this.correctFlagsCounter--;
            }
            totalFlagsCounter--;
        }
        getCellAt(x , y).invalidate();
        checkWinning();
    }

    private void onGameLost(){ //TODO: BANNNER ? ACTIVITY?

        Toast.makeText(context , "Gmae over " , Toast.LENGTH_SHORT).show();
    }

    private void checkWinning(){
        if (correctFlagsCounter == numOfMines){
            onGameWin();
        }
    }

    private void onGameWin(){//TODO: BANNNER ? ACTIVITY?
        Toast.makeText(context , "WIN! " , Toast.LENGTH_SHORT).show();
    }

    public int getTotalFlagsCounter() {
        return totalFlagsCounter;
    }
}