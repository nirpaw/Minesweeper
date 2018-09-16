package com.example.nir30.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_new);
    }
}


//        gameButtons = new ArrayList<>();
//      //  table = (TableLayout)findViewById(R.id.table_layout);
//
//        BoardGenerator gameBoardGenerator = new BoardGenerator(10,10,4);
//        logicBoard = gameBoardGenerator.getBoardMatrix();
//        numOfMines = gameBoardGenerator.getNumOfMines();
//        creatMatrix();
//    }

//
//    private void creatMatrix()
//    {
//        GameButtonListener gameButtonListener = new GameButtonListener();
//        GameButtonLongClickListener gameButtonLongClickListener = new GameButtonLongClickListener();
//        for(int row=0 ; row < logicBoard.length ;row++)
//        {
//            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.MATCH_PARENT,1.0f));
//            table.addView(tableRow);
//            for(int col= 0 ; col < logicBoard[0].length ; col++ )
//            {
//                GameButton gameButton = new GameButton(this, row, col);
//                gameButton.setLayoutParams(new TableRow.LayoutParams(
//                        TableRow.LayoutParams.MATCH_PARENT,
//                        TableRow.LayoutParams.MATCH_PARENT,1.0f));
//                gameButton.setOnClickListener(gameButtonListener);
//                gameButton.setOnLongClickListener(gameButtonLongClickListener);
//  //              gameButton.setBackgroundResource(R.color.colorPrimaryDark);
//                tableRow.addView(gameButton);
//                gameButtons.add(gameButton); // add to list of all buttons
//            }
//        }
//    }
//
//
//    // Long Press on button listener - Set Flag
//    public class GameButtonLongClickListener implements View.OnLongClickListener{
//        @Override
//        public boolean onLongClick(View view) {
//            if (((GameButton)view).isUserFlag())
//            {
//                ((GameButton)view).setUserFlag(false);
//                // TODO: animation of flag out
//                ((GameButton)view).setText(""); // for debug only
//            }else {
//                ((GameButton)view).setUserFlag(true);
//                // TODO: animation of flag IN
//                ((GameButton)view).setText("!"); // for debug only
//            }
//            return true;
//        }
//    }
//
//    // Click on button
//    public class GameButtonListener implements View.OnClickListener{
//        @Override
//        public void onClick(View view) {
//            int row = ((GameButton)view).i;
//            int col = ((GameButton)view).j;
//            if (logicBoard[row][col].isPressed())
//            {
//                return;
//            }
//            String str= "";
//
//            if(logicBoard[row][col].isMine())
//            {
//                str = "X"; // for debug GAMEOVER
//            }
//            if(logicBoard[row][col].getMinesAround() != 0)
//            {
//                str = logicBoard[row][col].getMinesAround() + "";
//      //          view.setBackgroundResource(R.color.colorAccent);
//            } else if(logicBoard[row][col].getMinesAround() == 0){
//               //
//                str = "[]";
//                pressBtn(row, col);
//            }
//            ((GameButton)view).setText(str);
//        }
//    }
//
//    private void  pressBtn(int row, int col){
//        if(logicBoard[row][col].isMine())
//        {
//            // GAME OVER
//            return;
//        }
//        if (logicBoard[row][col].getMinesAround() == 0){
//            for(int i = row - 1 ; i <= row + 1 ; i++){
//                for (int j = col - 1 ; j <= col + 1 ; j++ ){
//                    if(!(i < 0 || i >= logicBoard.length || j < 0 || j >= logicBoard[0].length))
//                    {
//                        pressBtn(i, j);
//                    }
//                }
//            }
//        } else {
//            logicBoard[row][col].isPressed();
//        }
//        refreshMatrixButtons();
//    }
//
//    private void refreshMatrixButtons(){
//        for (GameButton button : this.gameButtons) {
//            if (logicBoard[button.i][button.j].isPressed())
//            {
//                button.setClickable(false);
//                button.setText("[]");
//            }
//
//        }
//    }
