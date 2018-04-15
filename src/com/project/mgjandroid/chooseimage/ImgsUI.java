package com.project.mgjandroid.chooseimage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.PublishEducationInfoActivity;
import com.project.mgjandroid.ui.activity.homemaking.PublishHomeMakingInfoActivity;
import com.project.mgjandroid.ui.activity.renthouse.PublishHouseLeaseInfoActivity;
import com.project.mgjandroid.ui.activity.PublishUsedGoodsInfoActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.repair.PublishRepairInfoActivity;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;


@SuppressLint("UseSparseArrays")
@Router("imgs_ui")
public class ImgsUI extends BaseActivity implements OnClickListener {

	@InjectView(R.id.gridView1)
	private GridView imgGridView;
	@InjectView(R.id.photo_confirm)
	private TextView tvConfirm;
	@InjectView(R.id.photo_act_iv_back)
	private ImageView ivBack;
	@InjectView(R.id.photo_gallery_title)
	private TextView toolbar;

	private FileTraversal fileTraversal;
	private ArrayList<String> filelist;
	private ArrayList<String> tempFilelist = new ArrayList<>();
	private int MAX_COUNT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_gallery);
		Injector.get(this).inject();
		initData();
	}

	protected void initData() {
		ivBack.setOnClickListener(this);
		tvConfirm.setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		fileTraversal = bundle.getParcelable("data");
		if(fileTraversal == null) return;
		Collections.reverse(fileTraversal.filecontent);
		filelist = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
		for(String s:filelist){
			tempFilelist.add(s);
		}
		ImgsAdapter imgsAdapter = new ImgsAdapter(this, (ArrayList<String>) fileTraversal.filecontent, tempFilelist, onItemClickListener);
		imgGridView.setAdapter(imgsAdapter);
		MAX_COUNT = ChoosePhotoModel.getInstance().getMaxCount();
		toolbar.setText(String.format(getString(R.string.has_choose_img), tempFilelist.size() + "/" + MAX_COUNT));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.photo_confirm:
				sendfiles();
				break;
			case R.id.photo_act_iv_back:
				tobreak();
				break;
		}
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void OnItemClick(View v, int position, CheckBox checkBox, ImageView imge_selected, ImageView image) {
			String filapath = fileTraversal.filecontent.get(position);
			if (checkBox.isChecked()) {
				checkBox.setChecked(false);
				if (imge_selected!=null) {
					imge_selected.setVisibility(View.INVISIBLE);
				}
				image.setColorFilter(Color.parseColor("#00000000"));
				tempFilelist.remove(filapath);
				toolbar.setText(String.format(getString(R.string.has_choose_img), tempFilelist.size() + "/" + MAX_COUNT));
			} else {
				if (tempFilelist.size() >= MAX_COUNT) {
					ToastUtils.displayMsg(String.format(getString(R.string.choose_img_max_count), MAX_COUNT + ""), ImgsUI.this);
                    return;
                }
				checkBox.setChecked(true);
				if (imge_selected!=null) {
                    imge_selected.setVisibility(View.VISIBLE);
                }
				image.setColorFilter(Color.parseColor("#80000000"));
				Log.i("img", "img choise position->" + position);
				tempFilelist.add(filapath);
				toolbar.setText(String.format(getString(R.string.has_choose_img), tempFilelist.size() + "/" + MAX_COUNT));
			}
		}
	};

	public void tobreak() {
		startActivity(new Intent(ImgsUI.this, ImgFolderListUI.class));
		finish();
	}

	public void sendfiles() {
		ChoosePhotoModel.getInstance().setmCurrentPhotoList(tempFilelist);
		for (String fileName : filelist) {
			MLog.d("send file:" + fileName);
		}
		if(PublishUsedGoodsInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
				|| PublishHouseLeaseInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
				|| PublishEducationInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
				|| PublishHomeMakingInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
				|| PublishRepairInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())) {
			ChoosePhotoModel.getInstance().setFrom("");
			Intent intent = new Intent(this, UploadPhotoActivity.class);
			startActivity(intent);
		}
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			tobreak();
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			tobreak();
			return true ;
		}
		return super.onOptionsItemSelected(item);
	}
}
