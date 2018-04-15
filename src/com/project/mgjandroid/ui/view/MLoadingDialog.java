package com.project.mgjandroid.ui.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.project.mgjandroid.R;

public class MLoadingDialog extends DialogFragment {

	private String mMsg = "";

	public void setMsg(String msg) {
		this.mMsg = msg;
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_loading, null);
		TextView title = (TextView) view
				.findViewById(R.id.id_dialog_loading_msg);
		if(!TextUtils.isEmpty(mMsg)){
			title.setVisibility(View.VISIBLE);
			title.setText(mMsg);
		}
		Dialog dialog = new Dialog(getActivity(), R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view);
		return dialog;
	}

}