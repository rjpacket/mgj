package com.project.mgjandroid.ui.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**跑马灯TextView
 * @author jian
 *
 */
public class AlwaysMarqueeTextView extends TextView {
	public static final int SPEED = 0;

	public AlwaysMarqueeTextView(Context context) {
		super(context);
		setMarqueeSpeed(this, SPEED, false);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setMarqueeSpeed(this, SPEED, false);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setMarqueeSpeed(this, SPEED, false);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
	protected void setMarqueeSpeed(TextView tv, float speed, boolean speedIsMultiplier) {

		try {
			Field f = tv.getClass().getDeclaredField("mMarquee");
			f.setAccessible(true);
			Object marquee = f.get(tv);
			if (marquee != null) {
				Field mf = marquee.getClass().getDeclaredField("mScrollUnit");
				mf.setAccessible(true);
				float newSpeed = speed;
				if (speedIsMultiplier) {
					newSpeed = mf.getFloat(marquee) * speed;
				}
				mf.setFloat(marquee, newSpeed);
//				Log.i("People", String.format("%s marquee speed set to %f", tv, newSpeed));
			}
		} catch (Exception e) {
			// ignore, not implemented in current API level
		}
	}
}
