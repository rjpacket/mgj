package com.project.mgjandroid.inter_face;

/**
 * Created by yuandi on 2016/7/8.
 *
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
