package me.ideatolife.testproject.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public GridSpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)==0 || parent.getChildAdapterPosition(view)==1){
            outRect.set(space/2, space, space/2, space);
        }else {
            outRect.set(space/2, space/2, space/2, space/2);
        }
    }
}