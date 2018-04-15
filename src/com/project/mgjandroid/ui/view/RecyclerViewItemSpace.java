package com.project.mgjandroid.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by thinkpad on 2016/7/9.
 *
 */
public class RecyclerViewItemSpace extends RecyclerView.ItemDecoration {

    private int space;
    /**
     * 每行个数，默认为4
     */
    private int count = 4;

    public RecyclerViewItemSpace (int space, int count) {
        this.space = space;
        this.count = count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = 2 * space;
//        if(count != 0 && parent.getChildLayoutPosition(view) % count == 0) {
//            outRect.left = 0;
//        }
    }
}
