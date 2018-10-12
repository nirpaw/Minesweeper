package com.example.nir30.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener{
    private static long timeWhenStopped = 0;
    public static SharedPreferences sp;
    private static Chronometer cmTimer;
    private static boolean isRunningTimer = false;
    private static ImageButton pauseBtn;
    private static ImageView restartGameIB;
    public static boolean sessionInProgress = true;
    static TextView minesLeftTV;
    public static boolean ifTheGameEnd = false, ifAnim = false;
    private static String bestScoreSp = "bestScore", detailsInSp = "detailsOfScore",countOfWinsSp = "countOfWin",countOfGamesSp = "countOfGames",countOfLosesSp = "countOfLoses";

    AlertDialog.Builder alertDialogBuilderPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        int numOfCols = intent.getIntExtra("widthSize",0);
        int numOfRows = intent.getIntExtra("heightSize",0);
        int numOfMines = intent.getIntExtra("numOfMines",0);

        SessionManager.getInstance();

        sp= getSharedPreferences(detailsInSp,MODE_PRIVATE);


        SessionManager.numOfCols = numOfCols;
        SessionManager.numOfRows = numOfRows;
        SessionManager.numOfMines = numOfMines;

        setContentView(R.layout.activity_game_new);

        minesLeftTV = findViewById(R.id.mines_left_Tv);
        minesLeftTV.setText(numOfMines+"");

        cmTimer = findViewById(R.id.cmTimer);

        pauseBtn = findViewById(R.id.pauseBtn);
        pauseBtn.setOnClickListener(this);

        restartGameIB = findViewById(R.id.restartGameIB);
        restartGameIB.setOnClickListener(this);

//        int countGames = sp.getInt(countOfGamesSp,0);
//        if (countGames >0) {
//        }
        resetChronometer(cmTimer);

        startChronometer(cmTimer);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pauseBtn) {
            pauseChronometer(cmTimer);
            startBlink(cmTimer, 800,4);
            alertDialogBuilderPause = new AlertDialog.Builder(GameActivity.this);
            alertDialogBuilderPause.setTitle(R.string.pause_dialog);
            alertDialogBuilderPause
                    .setCancelable(false)
                    .setPositiveButton(R.string.quit_btn,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            GameActivity.this.finish();
                        }
                    })
                    .setNegativeButton(R.string.stay_btn,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                            startChronometer(cmTimer);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilderPause.create();
            alertDialog.show();

        }
        else if (view.getId() == R.id.restartGameIB) {
                if (!ifAnim) {
                    restartGameIB.animate().rotationX(360).setDuration(2000).start();
                    ifAnim = true;
                }
                else {
                    restartGameIB.animate().rotationX(0).setDuration(2000).start();
                    ifAnim = false;
                }

                resetChronometer(cmTimer);
                startChronometer(cmTimer);
                ifTheGameEnd = false;
                restartGameIB.setImageResource(R.drawable.smile0);

                SessionManager.getInstance().resetBoard();
        }
    }

    public void startChronometer (View view) {
        if (!isRunningTimer) {
            cmTimer.setBase(SystemClock.elapsedRealtime() - timeWhenStopped);
            cmTimer.start();
            isRunningTimer= true;
        }
    }

    public static void pauseChronometer (View view) {
        if (isRunningTimer) {
            cmTimer.stop();
            timeWhenStopped = SystemClock.elapsedRealtime() - cmTimer.getBase();
            isRunningTimer = false;
        }
    }

    public static void resetChronometer (View view) {
        cmTimer.setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
        pauseBtn.setClickable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Tag", "onResume: ");
        if (!ifTheGameEnd)
            startChronometer(cmTimer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Tag", "onPause: ");
        pauseChronometer(cmTimer);
    }

    @Override
    public void onBackPressed() {
        pauseChronometer(cmTimer);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
        alertDialogBuilder.setTitle(R.string.exit_title);
        alertDialogBuilder
                .setMessage(R.string.sure_to_quit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_quit_btn,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        GameActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no_quit_btn,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        startChronometer(cmTimer);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void changeMinesLeftTV(int minesLeft){
        minesLeftTV.setText(minesLeft+"");
    }

    public static void onGameActivityWin(final Context context, int gameScore, String timeWinResult){
        pauseChronometer(cmTimer);
        int bestScoreNumber = 0, countWins,totalGames;
        TextView bestScoreTv;
        ImageView newRecordIv;
        ifTheGameEnd = true;
        Button startNewGameBtn,quitGameBtn;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.win_page);

        TextView timeWinTv = dialog.findViewById(R.id.time_result_Tv);
        timeWinTv.setText(timeWinResult);

        TextView scoreWimTv = dialog.findViewById(R.id.score_result_Tv);
        scoreWimTv.setText(gameScore + "");

        bestScoreNumber = sp.getInt(bestScoreSp,0);
        newRecordIv = dialog.findViewById(R.id.new_record_Iv);

        if (bestScoreNumber<gameScore) {
            bestScoreNumber = gameScore;
            newRecordIv.setVisibility(View.VISIBLE);

        }
        else {
            newRecordIv.setVisibility(View.INVISIBLE);
        }

        countWins = sp.getInt(countOfWinsSp,0);
        countWins++;
        totalGames = sp.getInt(countOfGamesSp,0);
        totalGames++;

        SharedPreferences.Editor editor = sp.edit();
        bestScoreTv = dialog.findViewById(R.id.best_score_result_Tv);
        bestScoreTv.setText(bestScoreNumber +"");
        editor.putInt(bestScoreSp,bestScoreNumber);
        editor.putInt(countOfWinsSp,countWins);
        editor.putInt(countOfGamesSp,totalGames);
        editor.commit();
        dialog.setCancelable(false);



        dialog.show();

        startNewGameBtn =(Button) dialog.findViewById(R.id.start_new_game_btn_after_win);
        startNewGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                restartGameIB.callOnClick();
            }
        });

        quitGameBtn = dialog.findViewById(R.id.quit_btn);
        quitGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });

    }


    public static void onGameActivityLost(){
        pauseChronometer(cmTimer);
        ifTheGameEnd = true;
        restartGameIB.setImageResource(R.drawable.sad_smile);
        pauseBtn.setClickable(false);

        int countLoses,totalGames;

        countLoses = sp.getInt(countOfLosesSp,0);
        countLoses++;
        totalGames = sp.getInt(countOfGamesSp,0);
        totalGames++;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(countOfLosesSp,countLoses);
        editor.putInt(countOfGamesSp,totalGames);
        editor.commit();

    }

    public static String getCurrentTime(){
        int seconds=0;
        String timeString = cmTimer.getText().toString();
        return timeString;
    }


    public static void startBlink(View view, int speed, int duration){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(speed);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(duration);
        view.startAnimation(anim);
    }

}