package com.project.mgjandroid.constants;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;

/**
 * Created by ning on 2016/5/18.
 */
public class ActivitySchemeManager {
    public static final String SCHEME = App.getContext().getResources().getString(R.string.app_scheme) + "://";
    //photo_activity
    public static final String URL_PHOTO_VIEW = "photo_activity";
    //img_folder_list_ui
    public static final String URL_GET_IMAGE_LIST = "img_folder_list_ui";
    //feed_back_activity
    public static final String URL_FEED_BACK = "feed_back_activity";
}
