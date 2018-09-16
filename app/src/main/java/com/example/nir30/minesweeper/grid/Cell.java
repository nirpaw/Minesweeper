package com.example.nir30.minesweeper.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.nir30.minesweeper.SessionManager;
import com.example.nir30.minesweeper.R;

public class Cell extends BaseCell implements View.OnClickListener, View.OnLongClickListener{

    public Cell(Context context, int x , int y) {
        super(context);

        setPosition(x, y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        SessionManager.getInstance().flag(getXpos(), getYpos());
        return true;
    }

    @Override
    public void onClick(View view) {
        SessionManager.getInstance().click(getXpos(), getYpos());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBtn(canvas);

        if( isFlaged() ) {
            drawFlag(canvas);
        }else if( isRevealed() && isMine() && !isClicked()) {
            drawNormalMine(canvas);
        }else {
            if(isClicked()){
                if( getValue() == -1){
                    drawMineExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else {
                drawBtn(canvas);
            }
        }
    }
    private void drawFlag(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag);
        drawable.setBounds(0,0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawMineExploded(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_exploded);
        drawable.setBounds(0,0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalMine(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_normal);
        drawable.setBounds(0,0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawBtn(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button);
        drawable.setBounds(0,0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void drawNumber(Canvas canvas){
        Drawable drawable = null;
        switch (getValue()) {
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_8);
                break;
        }
        drawable.setBounds(0,0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
}


