package com.project.mgjandroid.ui.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author gaoj
 * 
 */
public class PullableWebView extends WebView implements Pullable {

	public PullableWebView(Context context) {
		super(context);
	}

	public PullableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

	}

	@Override
	public boolean canPullDown() {
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {
		float webViewContentHeight = getContentHeight() * getScale();
		// WebView的现高度
		float webViewCurrentHeight = (getHeight() + getScrollY());
		if (Math.abs(webViewContentHeight - webViewCurrentHeight) < 3) {
			// System.out.println("WebView滑动到了底端");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 不移动无法渲染
	 */
	public void scrollToPx() {
		int y = getScrollY() - 2;
		scrollTo(getScrollX(), y);
	}
}
