package com.project.mgjandroid.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;

public  class DialogUtil {
	
	private AlertDialog alertDialog;
	private LinearLayout layout;
	public static Context context;
	public SureInterfance listener;
	
	public interface SureInterfance{
		public void sureTodo();
	}
	
	public interface NativeInterfance{
		public void NativeTodo();
	}
	
	public DialogUtil(Context context, boolean l, boolean r, String AlertContent, String LContent, String RContent, final SureInterfance listener){
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.clear_cache_dialog, null);
		alertDialog = new AlertDialog.Builder(context).create();
		this.context = context;
		this.listener = listener;
		alertDialog.setCancelable(false);
		TextView txtContent = (TextView) layout.findViewById(R.id.txt_content);
		txtContent.setText(AlertContent);
		TextView btnCancle = (TextView)layout.findViewById(R.id.btn_cancle);

		LinearLayout layout_cancle = (LinearLayout) layout.findViewById(R.id.layout_cancle);
		btnCancle.setText(LContent);
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(alertDialog.isShowing())
				alertDialog.dismiss();
			}
		});


		TextView btnSure  = (TextView)layout.findViewById(R.id.btn_sure);
		btnSure.setText(RContent);
		btnSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.sureTodo();
				alertDialog.dismiss();
			}
		});
		
		if(!l){
			layout_cancle.setVisibility(View.GONE);
		}
		if(!r){
			btnSure.setVisibility(View.GONE);
		}
	}
	
	public DialogUtil(Context context, boolean l, boolean r, String AlertContent, String LContent, String RContent, final SureInterfance listener, final NativeInterfance nativelistener){

		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.clear_cache_dialog, null);
		alertDialog = new AlertDialog.Builder(context).create();
		this.context = context;
		this.listener = listener;
		TextView txtContent = (TextView) layout.findViewById(R.id.txt_content);
		txtContent.setText(AlertContent);
		TextView btnCancle = (TextView)layout.findViewById(R.id.btn_cancle);
		btnCancle.setText(LContent);

		TextView btnSure  = (TextView)layout.findViewById(R.id.btn_sure);
		btnSure.setText(RContent);
		
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nativelistener.NativeTodo();
			}
		});
		
		btnSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.sureTodo();
				alertDialog.dismiss();
			}
		});
		
		if(!l){
			btnCancle.setVisibility(View.GONE);
		}
		if(!r){
			btnSure.setVisibility(View.GONE);
		}
	
	}
	
	
	public  void showCustomDialog() {
		// TODO Auto-generated method stub
		alertDialog.show();
		
		alertDialog.setContentView(layout);
		Window window = alertDialog.getWindow();
		window.setBackgroundDrawable(new BitmapDrawable());
		LayoutParams layoutParams = window.getAttributes();
		WindowManager manager =window.getWindowManager();
		Display display = manager.getDefaultDisplay();
		layoutParams.width = (int) (display.getWidth()*0.8);
		window.setAttributes((WindowManager.LayoutParams) layoutParams);
	}
	
	
	
	public void nativeToDo(){
		
	}
	
	public void dismiss(){
		
	}
	
}
