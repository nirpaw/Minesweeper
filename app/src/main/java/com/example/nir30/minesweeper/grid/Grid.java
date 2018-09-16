package com.example.nir30.minesweeper.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.nir30.minesweeper.SessionManager;


public class Grid extends GridView{

    public Grid(Context context , AttributeSet attrs){
        super(context,attrs);
        SessionManager.getInstance().creatBoard(context);
        setNumColumns(SessionManager.numOfCols);
        setAdapter(new GridAddapter());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class GridAddapter extends BaseAdapter{
        @Override
        public int getCount() {
            return SessionManager.getInstance().getSize();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            return SessionManager.getInstance().getCellAt(position);
        }
    }

}

