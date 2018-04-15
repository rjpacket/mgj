package com.project.mgjandroid.ui.view.scrollloopviewpager.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * banner
 *
 * @author BoBoMEe
 */

public class MyBanner extends RelativeLayout {

    // 自动轮播的时间间隔
    private final static int TIME_INTERVAL = 3;
    // 自动轮播启用开关
    private final static boolean _isAutoPlay = true;
    private List<Object> bannerList = new ArrayList<>();
    private boolean imageNeedHandle;
    private boolean isFromHomePage = false;
    private AutoScrollViewPager viewPager;
    private Context context;
    private CircleIndicator pageIndex;
    private OnBannerItemClickListener onBannerItemClickListener;

    public MyBanner(Context context) {
        this(context, null);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void setBitmaps(List<Bitmap> list) {
        this.bannerList.clear();
        this.bannerList.addAll(list);
        run();
    }

    public void setUrls(List<String> list, boolean imageNeedHandle, boolean isFromHomePage) {
        this.bannerList.clear();
        this.bannerList.addAll(list);
        this.imageNeedHandle = imageNeedHandle;
        this.isFromHomePage = isFromHomePage;
        run();
    }

    public void setResources(List<Integer> list) {
        this.bannerList.clear();
        this.bannerList.addAll(list);
        run();
    }

    private void run() {
        initUI(this.context);
        if (_isAutoPlay) {
            viewPager.setInterval(TIME_INTERVAL * 1000);
            viewPager.startAutoScroll();
        }
    }

    private void initUI(Context context) {
        viewPager.setAdapter(new MyPagerAdapter(context, this.bannerList.toArray(), imageNeedHandle));

        pageIndex.setViewPager(viewPager);
        pageIndex.setSelectedPos(0);
        if(bannerList.size() < 2){
            pageIndex.setVisibility(INVISIBLE);
        }else{
            pageIndex.setVisibility(VISIBLE);
        }
    }

    private int downX;
    private int downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 父控件不要拦截
                getParent().requestDisallowInterceptTouchEvent(true);

                downX = (int) ev.getX();
                downY = (int) ev.getY();
                viewPager.stopAutoScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                // 下滑
                if (Math.abs(moveY - downY) > Math.abs(moveX - downX)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);

                }
                break;
            case MotionEvent.ACTION_CANCEL:
                viewPager.startAutoScroll();
                break;
            case MotionEvent.ACTION_UP:
                viewPager.startAutoScroll();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        if (!this.isInEditMode()) {

            // 1.Viewpager
            viewPager = (AutoScrollViewPager)
                    findViewById(R.id.picslooper);
            viewPager.setFocusable(true);

            // 2. indicator
            pageIndex = (CircleIndicator) findViewById(R.id.pageIndexor);
            pageIndex.setDotMargin(8);
            pageIndex.setPaddingBottom(6);
        }
        super.onFinishInflate();
    }

    public AutoScrollViewPager getViewPager() {
        return viewPager;
    }

    public void setPageIndex(CircleIndicator pageIndex) {
        this.pageIndex = pageIndex;
    }


    class MyPagerAdapter<T> extends MyBasePagerAdapter {
        private boolean imageNeedHandle;
        public MyPagerAdapter(Context context, T[] objects, boolean imageNeedHandle) {
            super(context, objects);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = (getCount() + position % getCount()) % getCount();
            ImageView iv = new ImageView(context);
//            iv.setRatio(4.17f);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Object obj = objects[position];
            if (obj instanceof Integer) {
                iv.setImageResource((Integer) obj);
            } else if(obj instanceof String) {
                if (isFromHomePage) {
                    iv.setImageResource(R.drawable.banner);
                    if(imageNeedHandle){
                        ImageUtils.loadBitmap(context , obj + Constants.IMAGE_URL_END_THUMBNAIL, iv, R.drawable.banner_default , "");
                    }else{
                        ImageUtils.loadBitmap(context , (String) obj, iv, R.drawable.banner_default , "");
                    }
                } else {
                    ImageUtils.loadBitmap(context , (String) obj, iv, R.drawable.goods_detail , "");
                }
            } else {
                iv.setImageBitmap((Bitmap) obj);
            }

            final int fPosition = position;
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onBannerItemClickListener) {
                        onBannerItemClickListener.onItemClick(fPosition);
                    }
                }
            });

            container.addView(iv);
            return iv;
        }

    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewPager.stopAutoScroll();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        viewPager.startAutoScroll();
    }

}
