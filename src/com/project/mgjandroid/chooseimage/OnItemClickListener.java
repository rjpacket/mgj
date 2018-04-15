package com.project.mgjandroid.chooseimage;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public interface OnItemClickListener {

    void OnItemClick(View v, int Position, CheckBox checkBox, ImageView imge_selected, ImageView image);
}
