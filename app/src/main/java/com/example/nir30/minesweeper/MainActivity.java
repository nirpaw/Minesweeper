package com.example.nir30.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
    public final int NUM_OF_MAX_CELLS = 16;
    TextView widthCustomTv,heightCustomTv,minesCustomTv;
    TextView beginnerTv,easyTv,normalTv,hardTv,challengingTv,customTv;
    AlertDialog.Builder builderForSelectLevel,builderForCustomLevel;
    AlertDialog alertDialogLevelBtn,alertDialogCustomBtn;
    SeekBar widthSB,heightSB,minesSB;
    Button finishCustomBtn,cancelCustomBtn;
    Button startNewGameBtn,levelBtn,statisticsBtn;

    public int widthSize=8, heightSize=8, mines=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNewGameBtn = findViewById(R.id.start_game_btn);
        Button startGameBtn = findViewById(R.id.start_game_btn);
        startGameBtn.setOnClickListener(this);

        levelBtn = findViewById(R.id.level_btn);
        levelBtn.setOnClickListener(this);

        startGameBtn = findViewById(R.id.statistic_btn);
        startGameBtn.setOnClickListener(this);

    }


    public void onClick(View view) {
       if (view.getId() == R.id.start_game_btn) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);

            intent.putExtra("widthSize" , widthSize);
            intent.putExtra("heightSize" , heightSize);
            intent.putExtra("numOfMines" , mines);
            startActivity(intent);
            SessionManager.getInstance().setTotalFlagsCounterToZero();
        }
        else if (view.getId() == R.id.level_btn) {

            builderForSelectLevel = new AlertDialog.Builder(MainActivity.this);
            View dialogViewSelectLevel = getLayoutInflater().inflate(R.layout.select_level,null);
            builderForSelectLevel.setTitle(R.string.select_level_btn);
            builderForSelectLevel.setView(dialogViewSelectLevel);
            alertDialogLevelBtn = builderForSelectLevel.show();

            beginnerTv = dialogViewSelectLevel.findViewById(R.id.beginner_Tv);
            beginnerTv.setOnClickListener(this);

            easyTv = dialogViewSelectLevel.findViewById(R.id.easy_Tv);
            easyTv.setOnClickListener(this);

            normalTv = dialogViewSelectLevel.findViewById(R.id.normal_Tv);
            normalTv.setOnClickListener(this);

            hardTv = dialogViewSelectLevel.findViewById(R.id.hard_Tv);
            hardTv.setOnClickListener(this);

            challengingTv = dialogViewSelectLevel.findViewById(R.id.challenging_Tv);
            challengingTv.setOnClickListener(this);

            customTv = dialogViewSelectLevel.findViewById(R.id.custom_Tv);
            customTv.setOnClickListener(this);

        }
        else if (view.getId() == R.id.statistic_btn) {
            Intent intent = new Intent(MainActivity.this,StatisticsActivity.class);
            startActivity(intent);
        }

        else if (view.getId() == R.id.custom_Tv) {
            alertDialogLevelBtn.cancel();

            builderForCustomLevel = new AlertDialog.Builder(MainActivity.this);
            final View dialogCustomLevel = getLayoutInflater().inflate(R.layout.custom_level,null);
            builderForCustomLevel.setView(dialogCustomLevel);
            builderForCustomLevel.setCancelable(false);
            alertDialogCustomBtn =  builderForCustomLevel.show();

            finishCustomBtn = dialogCustomLevel.findViewById(R.id.finish_select_custom_level_btn);
            finishCustomBtn.setOnClickListener(this);

            cancelCustomBtn = dialogCustomLevel.findViewById(R.id.cancel_select_custom_level_btn);
            cancelCustomBtn.setOnClickListener(this);

            widthCustomTv = dialogCustomLevel.findViewById(R.id.width_tv);
            heightCustomTv = dialogCustomLevel.findViewById(R.id.height_tv);
            minesCustomTv = dialogCustomLevel.findViewById(R.id.mines_tv);

            widthSB = dialogCustomLevel.findViewById(R.id.width_seekBar);
            heightSB = dialogCustomLevel.findViewById(R.id.height_seekBar);
            minesSB = dialogCustomLevel.findViewById(R.id.mines_seekBar);

            seekBarListenerClass seekBarListenerClass = new seekBarListenerClass(MainActivity.this,dialogCustomLevel);
            widthSB.setOnSeekBarChangeListener(seekBarListenerClass);
            heightSB.setOnSeekBarChangeListener(seekBarListenerClass);
            minesSB.setOnSeekBarChangeListener(seekBarListenerClass);

            widthSB.setMax(NUM_OF_MAX_CELLS);
            heightSB.setMax(NUM_OF_MAX_CELLS);
            widthSB.setProgress(widthSB.getMax()/2);
            heightSB.setProgress(heightSB.getMax()/2);

            minesSB.setMax((widthSB.getProgress()*heightSB.getProgress()) -5);
            minesSB.setProgress(minesSB.getMax()/2);
        }

        else if (view.getId() == R.id.beginner_Tv) {
            widthSize = 8;
            heightSize = 8;
            mines = 5;
            alertDialogLevelBtn.cancel();
        }
        else if (view.getId() == R.id.easy_Tv) {
            widthSize = 9;
            heightSize = 9;
            mines = 10;
            alertDialogLevelBtn.cancel();
        }
        else if (view.getId() == R.id.normal_Tv) {
            widthSize = 12;
            heightSize = 9;
            mines = 20;
            alertDialogLevelBtn.cancel();
        }
        else if (view.getId() == R.id.hard_Tv) {
            widthSize =16;
            heightSize = 9;
            mines = 30;
            alertDialogLevelBtn.cancel();
        }
        else if (view.getId() == R.id.challenging_Tv) {
            widthSize = 16;
            heightSize = 16;
            mines = 50;
            alertDialogLevelBtn.cancel();
        }

        else if (view.getId() == R.id.finish_select_custom_level_btn) {
            alertDialogCustomBtn.cancel();
            alertDialogLevelBtn.cancel();
            widthSize = Integer.parseInt(widthCustomTv.getText().toString());
            heightSize = Integer.parseInt(heightCustomTv.getText().toString());
            mines = Integer.parseInt(minesCustomTv.getText().toString());
       }
        else if (view.getId() == R.id.cancel_select_custom_level_btn) {
            alertDialogCustomBtn.cancel();
            alertDialogLevelBtn.show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(R.string.exit_title);
        alertDialogBuilder
                .setMessage(R.string.sure_to_quit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_quit_btn,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no_quit_btn,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_page_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help_btn) {
            Intent intent = new Intent(MainActivity.this,HelpActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.share_app_btn) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareText = getString(R.string.commend_share);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,getString(R.string.share_with)));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


class seekBarListenerClass implements SeekBar.OnSeekBarChangeListener  {

    private Activity mainActivity;
    private View view;
    TextView widthTv,heightTv,minesTv;
    SeekBar widthSB,heightSB,minesSB;

    seekBarListenerClass(Activity activity,View view) {
        this.mainActivity = activity;
        this.view = view;

    }

    public void setMinesSB (View view) {
        widthTv = view.findViewById(R.id.width_tv);
        heightTv = view.findViewById(R.id.height_tv);
        minesSB = view.findViewById(R.id.mines_seekBar);
        minesTv = view.findViewById(R.id.mines_tv);
        minesSB.setMax((Integer.parseInt(widthTv.getText().toString()) *Integer.parseInt(heightTv.getText().toString())-5));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

        if (seekBar.getId() == R.id.width_seekBar) {
            widthTv = view.findViewById(R.id.width_tv);
            heightSB = view.findViewById(R.id.height_seekBar);
            int height = heightSB.getProgress();
            if (progress < 4)
            {
                progress =4;
                widthSB = view.findViewById(R.id.width_seekBar);
                widthSB.setProgress(progress);
            }
            if (progress < height) {
                heightSB.setProgress(progress);
            }
            heightSB.setMax(progress);
            heightTv.setText(heightSB.getProgress()+"");
            widthTv.setText(progress +"");
            setMinesSB(view);
        }
        else if (seekBar.getId() == R.id.height_seekBar) {
            heightTv = view.findViewById(R.id.height_tv);
            widthSB = view.findViewById(R.id.width_seekBar);
            int width = widthSB.getProgress();
            if (progress > width)
                progress = width;
            if (progress < 4){
                progress =4;
                heightSB = view.findViewById(R.id.height_seekBar);
                heightSB.setProgress(progress);
            }

            heightTv.setText(progress+"");
            setMinesSB(view);
        }
        else if (seekBar.getId() == R.id.mines_seekBar) {
            minesTv = view.findViewById(R.id.mines_tv);
            if (progress <4){
                progress = 4;
                minesSB = view.findViewById(R.id.mines_seekBar);
                minesSB.setProgress(progress);
            }
            minesTv.setText(progress +"");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
