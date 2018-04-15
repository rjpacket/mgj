package com.project.mgjandroid.utils;

import java.lang.ref.WeakReference;
import java.util.Locale;

import com.project.mgjandroid.base.App;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ThemeSettingsHelper {
    public static final String THEME_DEFAULT = "default_theme";
    public static final String THEME_NIGHT = "night_theme";
    public static final int NIGHT_ALPHA = 150;
    public static final int DAY_ALPHA = 255;
    private static WeakReference<ThemeSettingsHelper> sThemeSettingsHelper;
    private String mThemePackageName;

    private ThemeSettingsHelper() {
        initTheme(PreferenceUtils.getStringPreference(PreferenceUtils.THEME_SETTING, ThemeSettingsHelper.THEME_DEFAULT, App.getInstance()));
    }

    private Object getDefaultResourceValue(int resId) {
        Resources localResources = App.getInstance().getResources();
        return getResourceValue(localResources, resId, localResources.getResourceTypeName(resId));
    }

    private Object getResourceValue(Resources paramResources, int resId, String resTypeName) {
        Object localObject = null;
        if ("drawable".equals(resTypeName)) {
            localObject = paramResources.getDrawable(resId);
        } else if ("color".equals(resTypeName)) {
            localObject = paramResources.getColorStateList(resId);
        }
        return localObject;
    }

    private Object getResourceValueByName(String resName, String resTypeName) throws Resources.NotFoundException {
        Context context = App.getInstance();
        String str = resName.toLowerCase(Locale.ENGLISH).trim();
        Resources resources = context.getResources();
        int id = resources.getIdentifier(str, resTypeName, context.getPackageName());
        Object localObject = null;
        if (id != 0) {
            localObject = getResourceValue(resources, id, resTypeName);
        }
        if (localObject == null) {
            throw new Resources.NotFoundException();
        }
        return localObject;
    }

    private Object getThemeResource(int paramInt) {
        Object object = null;
        Context context = App.getInstance();
        if (ThemeSettingsHelper.THEME_NIGHT.equals(this.mThemePackageName)) {
            try {
                Resources localResources = context.getResources();
                String str3 = "night_" + localResources.getResourceEntryName(paramInt);
                String str4 = localResources.getResourceTypeName(paramInt);
                Object localObject = getResourceValueByName(str3, str4);
                object = localObject;
            } catch (Resources.NotFoundException localNotFoundException1) {
                if (object == null)
                    object = getDefaultResourceValue(paramInt);
            }
        } else if (ThemeSettingsHelper.THEME_DEFAULT.equals(this.mThemePackageName)) {
            try {
                Resources localResources = context.getResources();
                String str1 = localResources.getResourceEntryName(paramInt);
                String str2 = localResources.getResourceTypeName(paramInt);
                Object localObject = getResourceValueByName(str1, str2);
                object = localObject;
            } catch (Resources.NotFoundException localNotFoundException1) {
                if (object == null)
                    object = getDefaultResourceValue(paramInt);
            }
        }
        return object;
    }

    private int getThemeResourceId(int paramInt) {
        Context context = App.getInstance();
        int resId = 0;
        Resources localResources = context.getResources();
        if (ThemeSettingsHelper.THEME_DEFAULT.equals(mThemePackageName)) {
            resId = paramInt;
        } else if (ThemeSettingsHelper.THEME_NIGHT.equals(mThemePackageName)) {
            String str1 = "night_" + localResources.getResourceEntryName(paramInt);
            String str2 = localResources.getResourceTypeName(paramInt);
            resId = localResources.getIdentifier(str1, str2, context.getPackageName());
            if (resId == 0) {
                resId = paramInt;
            }
        }
        return resId;
    }

    public int getThemeResourceId(String paramStr) {
        Context context = App.getInstance();
        int resId = 0;
        Resources localResources = context.getResources();
        int paramInt = localResources.getIdentifier(paramStr, "drawable", context.getPackageName());
        if (ThemeSettingsHelper.THEME_DEFAULT.equals(mThemePackageName)) {
            resId = paramInt;
        } else if (ThemeSettingsHelper.THEME_NIGHT.equals(mThemePackageName)) {
            String str1 = "night_" + paramStr;
            resId = localResources.getIdentifier(str1, "drawable", context.getPackageName());
            if (resId == 0) {
                resId = paramInt;
            }
        }
        return resId;
    }

    public int getNightResourceId(int paramInt) {
        Context context = App.getInstance();
        int resId = 0;
        Resources localResources = context.getResources();
        String str1 = "night_" + localResources.getResourceEntryName(paramInt);
        String str2 = localResources.getResourceTypeName(paramInt);
        resId = localResources.getIdentifier(str1, str2, context.getPackageName());
        if (resId == 0) {
            resId = paramInt;
        }
        return resId;
    }

    public static final ThemeSettingsHelper getThemeSettingsHelper() {
        if ((sThemeSettingsHelper == null) || (sThemeSettingsHelper.get() == null))
            sThemeSettingsHelper = new WeakReference<ThemeSettingsHelper>(new ThemeSettingsHelper());
        return sThemeSettingsHelper.get();
    }

    private void initTheme(String paramString) {
        this.mThemePackageName = paramString;
    }

    public void changeTheme(String paramString) {
        String str = mThemePackageName;
        initTheme(paramString);
        if (!this.mThemePackageName.equals(str)) {
            PreferenceUtils.saveStringPreference(PreferenceUtils.THEME_SETTING, mThemePackageName, App.getInstance());
        }
    }

    public String getCurrentThemePackage() {
        return mThemePackageName;
    }

    public ColorStateList getThemeColor(int viewId) {
        return (ColorStateList) getThemeResource(viewId);
    }

    public Drawable getThemeDrawable(int viewId) {
        return (Drawable) getThemeResource(viewId);
    }

    public boolean isDefaultTheme() {
        return ThemeSettingsHelper.THEME_DEFAULT.equals(this.mThemePackageName);
    }

    public void setImageViewSrc(Activity paramActivity, int paramInt1, int paramInt2) {
        ImageView localImageView = (ImageView) paramActivity.findViewById(paramInt1);
        if (localImageView != null)
            setImageViewSrc(localImageView, paramInt2);
    }

    public void setImageViewAlpha(Activity paramActivity, int paramInt1) {
        ImageView localImageView = (ImageView) paramActivity.findViewById(paramInt1);
        if (localImageView != null) {
            setImageViewAlpha(localImageView);
        }
    }

    public void setImageViewAlpha(ImageView paramImageView) {
//		if (paramImageView != null) {
//			if (!isDefaultTheme()) {
//                CompatUtils.setImageViewAlpha(paramImageView, NIGHT_ALPHA);
//			} else {
//                CompatUtils.setImageViewAlpha(paramImageView, DAY_ALPHA);
//			}
//		}
    }

    public void setImageViewSrc(ImageView paramImageView, int paramInt) {
        int resId = getThemeResourceId(paramInt);
        if (resId != 0) {
            paramImageView.setImageDrawable(App.getInstance().getResources().getDrawable(resId));
        }
    }

    public void setImageViewBitmap(ImageView paramImageView, int paramInt) {
        int resId = getThemeResourceId(paramInt);
        if (resId != 0) {
            paramImageView.setImageBitmap(ImageUtils.readBitmapFromResource(paramInt));
        }
    }

    public void setImageViewBitmap(ImageView paramImageView, int paramInt, int reqWidth, int reqHeight) {
        int resId = getThemeResourceId(paramInt);
        if (resId != 0) {
            paramImageView.setImageBitmap(ImageUtils.readBitmapFromResource(paramInt, reqWidth, reqHeight));
        }
    }

    public void setListViewDivider(Activity paramActivity, int paramInt1, int paramInt2) {
        ListView localListView = (ListView) paramActivity.findViewById(paramInt1);
        if (localListView != null)
            setListViewDivider(localListView, paramInt2);
    }

    public void setListViewDivider(ListView paramListView, int paramInt) {
        paramListView.setDivider(getThemeDrawable(paramInt));
    }

    public void setListViewSelector(Activity paramActivity, int paramInt1, int paramInt2) {
        ListView localListView = (ListView) paramActivity.findViewById(paramInt1);
        if (localListView != null)
            setListViewSelector(localListView, paramInt2);
    }

    public void setListViewSelector(ListView paramListView, int paramInt) {
        paramListView.setSelector(getThemeDrawable(paramInt));
    }

    public void setGridViewSelector(Activity paramActivity, int paramInt1, int paramInt2) {
        GridView localGridView = (GridView) paramActivity.findViewById(paramInt1);
        if (localGridView != null)
            setGridViewSelector(localGridView, paramInt2);
    }

    public void setGridViewSelector(GridView paramGridView, int paramInt) {
        paramGridView.setSelector(getThemeDrawable(paramInt));
    }

    public void setTextViewColor(Activity paramActivity, int paramInt1, int paramInt2) {
        TextView localTextView = (TextView) paramActivity.findViewById(paramInt1);
        if (localTextView != null)
            setTextViewColor(localTextView, paramInt2);
    }

    public void setTextViewColor(TextView paramTextView, int paramInt) {
        int resId = getThemeResourceId(paramInt);
        if (resId != 0) {
            paramTextView.setTextColor(App.getInstance().getResources().getColor(resId));
        }
    }

    //返回对应资源的主题ID
    public int getResourceThemeId(int paramInt) {
        return getThemeResourceId(paramInt);
    }

    public void setViewBackgroundDrawable(Activity paramActivity, int viewId, int resId) {
        View localView = paramActivity.findViewById(viewId);
        if (localView != null)
            setViewBackgroundDrawable(localView, resId);
    }

    public void setFrameLayoutForegroudDrawable(Activity paramActivity, int viewId, int resId) {
        FrameLayout localView = (FrameLayout) paramActivity.findViewById(viewId);
        if (localView != null)
            setFrameLayoutForegroudDrawable(localView, resId);
    }

    public void setProgressBarProgressDrawable(Activity paramActivity, int viewId, int resId) {
        View localView = paramActivity.findViewById(viewId);
        if (localView != null)
            setProgressDrawable(localView, resId);
    }

    private void setProgressDrawable(View view,
                                     int resId) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();

        int backResId = getThemeResourceId(resId);
        if (backResId != 0) {
            ((ProgressBar) view).setProgressDrawable(App.getInstance().getResources().getDrawable(backResId));
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setViewBackgroundDrawable(View view, int resId) {
        if (view == null) {
            return;
        }
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();

        int backResId = getThemeResourceId(resId);
        if (backResId != 0) {
            view.setBackgroundResource(backResId);
        }
//		view.setBackgroundDrawable(getThemeDrawable(context, resId));
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setFrameLayoutForegroudDrawable(FrameLayout view, int resId) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        view.setForeground(getThemeDrawable(resId));
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setViewBackgroudColor(Activity paramActivity, int viewId, int resId) {
        View localView = paramActivity.findViewById(viewId);
        if (localView != null)
            setViewBackgroudColor(localView, resId);
    }

    public void setViewBackgroudColor(View paramView, int resId) {
        int colorResId = getThemeResourceId(resId);
        if (colorResId != 0) {
            paramView.setBackgroundColor(App.getInstance().getResources().getColor(colorResId));
        }
//		paramView.setBackgroundColor(getThemeColor(context, resId).getDefaultColor());
    }

    public void setWindowBackgroud(Activity activity, int resId) {
        int colorResId = getThemeResourceId(resId);
        if (colorResId != 0) {
            activity.getWindow().setBackgroundDrawable(App.getInstance().getResources().getDrawable(colorResId));
        }
    }

//	public void setViewBackgroundDrawableTile(Activity paramActivity, int viewId,int resId,TileMode tileX,TileMode tileY) {
//		BitmapDrawable tileMe=(BitmapDrawable)getThemeDrawable(resId);
//		tileMe.setTileModeX(tileX);
//		tileMe.setTileModeY(tileY);
//		if(paramActivity.findViewById(viewId)!=null){
//			paramActivity.findViewById(viewId).setBackgroundDrawable(tileMe);
//		}
//	}

//	public void setEditTextHintColor(Context context, EditText paramEditView, int paramInt) {
//        int colorResId=getThemeResourceId(paramInt);
//        if(colorResId!=0){
//            paramEditView.setHintTextColor(context.getResources().getColor(colorResId));
//        }
////		paramEditView.setHintTextColor(getThemeColor(context, paramInt));
//	}
}