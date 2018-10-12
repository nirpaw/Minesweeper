package com.example.nir30.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StatisticsActivity extends Activity implements View.OnClickListener ,PopupMenu.OnMenuItemClickListener{

    private SharedPreferences sp;
    ImageButton settingsBtn;
    TextView winsTv,totalTv,bestSCoreTv;
    private static String bestScoreSp = "bestScore", detailsInSp = "detailsOfScore",countOfWinsSp = "countOfWin",countOfGamesSp = "countOfGames",countOfLosesSp = "countOfLoses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        sp = getSharedPreferences(detailsInSp,MODE_PRIVATE);
        setTextView();

        settingsBtn = findViewById(R.id.settings_IB);
        settingsBtn.setOnClickListener(this);
        registerForContextMenu(settingsBtn);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.settings_IB) {
            PopupMenu popup = new PopupMenu(StatisticsActivity.this,settingsBtn);
            popup.getMenuInflater()
                    .inflate(R.menu.statistics_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show(); //showing popup menu
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.clear_statistics_btn) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(bestScoreSp,0);
            editor.putInt(countOfWinsSp,0);
            editor.putInt(countOfGamesSp,0);
            editor.commit();
            setTextView();
            return true;
        }
        else if (menuItem.getItemId() == R.id.share_statistics_btn) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            int score = sp.getInt(bestScoreSp,0);
            String shareText = getString(R.string.text_to_share) + score +". " + getString(R.string.can_you_beat);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,getString(R.string.share_with)));
            return true;
        }

        return false;
    }

    public void setTextView () {
        int countTotalGame = sp.getInt(countOfGamesSp,0);
        int countGamesWon = sp.getInt(countOfWinsSp,0);
        int bestScore = sp.getInt(bestScoreSp,0);

        totalTv = findViewById(R.id.total_games_numberTv);
        totalTv.setText(countTotalGame +"");

        winsTv =  findViewById(R.id.games_won_numberTv);
        winsTv.setText(countGamesWon+"");

        bestSCoreTv = findViewById(R.id.best_score_numberTv);
        bestSCoreTv.setText(bestScore +"");
    }

}
