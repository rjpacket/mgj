package com.project.mgjandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;

import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationImageView extends ImageView {
	public static final String TAG = "LocationImageView";

	private static final Map<String, SoftReference<Bitmap>> imageAche
			= new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public LocationImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param
	 */
	public LocationImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param context
	 * @param
	 * @param
	 */
	public LocationImageView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 可以是中文地址。也可以是坐标，坐标经纬度用逗号隔开。
	 * @param place
	 */
	public void setMapView(String place){
		SoftReference reference = imageAche.get(place);
		if(reference != null){
			Bitmap bitmap = (Bitmap) reference.get();
			if(bitmap == null){
				new LoadMapImageTask().execute(place,"http://mengqitech.com/mobilecrm/static/map_pins/company_geo_pin.png");
			}else {
				setImageBitmap(bitmap);
			}
		}else {
			new LoadMapImageTask().execute(place,"http://mengqitech.com/mobilecrm/static/map_pins/company_geo_pin.png");
		}
	}

//	@Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//		int width = MeasureSpec.getSize(widthMeasureSpec);
//		int height = width*708/1317;
//	    super.onMeasure(widthMeasureSpec,
//	    		MeasureSpec.makeMeasureSpec(height,
//	    		MeasureSpec.getMode(heightMeasureSpec)));
//    }

	private final class LoadMapImageTask extends AsyncTask<String,Void,Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params)
		{
			InputStream stream = null;
			try
			{
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("center", params[0]));
//				nameValuePairs.add(new BasicNameValuePair("width", CommonUtils.getScreenWidth());
//				nameValuePairs.add(new BasicNameValuePair("height", DipToPx.dip2px(getContext(),200)+""));
				nameValuePairs.add(new BasicNameValuePair("markers", params[0]));
				nameValuePairs.add(new BasicNameValuePair("markerStyles", "-1,"+params[1]+",-1"));
				nameValuePairs.add(new BasicNameValuePair("zoom", "12"));

//				stream = HttpClientImp.INSTANCE.getForStream("http://api.map.baidu.com/staticimage", null,nameValuePairs);
				Bitmap bitmap = BitmapFactory.decodeStream(stream);
				imageAche.put(params[0], new SoftReference(bitmap));

				return bitmap;
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}finally{
				try
				{
					stream.close();
				}
				catch (Exception e2)
				{
					// TODO: handle exception
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result)
		{
			setImageBitmap(result);
//			result = null;
			super.onPostExecute(result);
		}
	}

}