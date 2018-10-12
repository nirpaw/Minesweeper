package com.example.nir30.minesweeper;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.nir30.minesweeper.Generator.BoardGenerator;
import com.example.nir30.minesweeper.grid.Cell;

public class SessionManager {

    private int totalFlagsCounter = 0;
    private static SessionManager instance;
    private Context context;
    int[][] gameBoard; // generated logic board
    Cell[][] MinesweeperGrid ;
    public boolean gameIsOver = false;
    public static int numOfMines = 2;
    public static int numOfRows = 8;
    public static int numOfCols = 8;

    public static int getSize () {
        return numOfRows * numOfCols;
    }

    public static SessionManager getInstance() {
        if (instance == null ) {
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager() {
    }

    public void setGridContext(){
        this.context = context;
    }

    public void createBoard(Context context) {
        this.context = context;
        // Create the board and store it
        this.gameIsOver = false;
        MinesweeperGrid = new Cell[numOfRows][numOfCols];
        BoardGenerator gameBoardGenerator = new BoardGenerator(numOfRows, numOfCols,  numOfMines);
        gameBoard = gameBoardGenerator.getBoardMatrix();
        setGrid(context, gameBoard);
    }

    public void resetBoard(){
        this.totalFlagsCounter = 0;
        this.gameIsOver = false;
        BoardGenerator gameBoardGenerator = new BoardGenerator(numOfRows, numOfCols,  numOfMines);
        gameBoard = gameBoardGenerator.getBoardMatrix();
        setGrid(context, gameBoard);
        GameActivity.changeMinesLeftTV(numOfMines - totalFlagsCounter);
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
            if(gameIsOver)
            {
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
                GameActivity.startBlink(getCellAt(Xpos, Ypos), 100,5);
                onGameLost();
            }
        }
        checkWinning();
    }

    public void flag(int x , int y){
        if(gameIsOver){
            return;
        }
        if(getCellAt(x,y).isClicked()){
            return;
        }
        boolean isFlagged = getCellAt(x, y).isFlaged();
        if(!isFlagged) {
            getCellAt(x, y).setFlaged(true);
            totalFlagsCounter++;
        }else {
            getCellAt(x,y).setFlaged(false);
            totalFlagsCounter--;
        }
        GameActivity.changeMinesLeftTV(numOfMines - totalFlagsCounter);
        getCellAt(x , y).invalidate();
    }
    public  void setTotalFlagsCounterToZero(){
        totalFlagsCounter = 0;
    }

    private void onGameLost(){
        gameIsOver = true;
        MediaPlayer ring= MediaPlayer.create(context, R.raw.bombexplodingsound);
        ring.start();
        RevealedAllCells();
        GameActivity.onGameActivityLost();
    }
    private void checkWinning(){
        int revealedsCounter = 0 ;
        for(int i = 0 ; i < numOfRows ; i++){
            for(int j = 0 ; j < numOfCols ; j++){
                if(MinesweeperGrid[i][j].isRevealed()){
                    revealedsCounter++;
                }
            }
        }
        if (revealedsCounter == getSize()-numOfMines){
            onGameWin();
        }
    }

    private void onGameWin(){
        gameIsOver = true;
        String timeString = GameActivity.getCurrentTime();
        String [] strArr = timeString.split(":");
        int seconds=0;
        seconds += Integer.parseInt(strArr[1]);
        seconds += Integer.parseInt(strArr[0]) * 60;

        RevealedAllCells();
        GameActivity.sessionInProgress = false;
        int gameScore = calculateScore(seconds);
        GameActivity.onGameActivityWin(context, gameScore,timeString);

    }

    private int calculateScore(int intTime){
        double size = getSize();
        double time = intTime;
        Double score = ((numOfMines / size * 0.5) +  (1 / time * 0.5)) * 1000;
        return score.intValue();
    }

    private void RevealedAllCells(){
        for(int i = 0 ; i < numOfRows; i++){
            for(int j = 0 ; j < numOfCols ; j++){
                getCellAt(i,j).setRevealed(true);
            }
        }
    }
    public int getTotalFlagsCounter() {
        return totalFlagsCounter;
    }

}