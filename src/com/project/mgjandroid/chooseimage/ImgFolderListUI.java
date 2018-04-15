package com.project.mgjandroid.chooseimage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Router("img_folder_list_ui")
public class ImgFolderListUI extends BaseActivity implements OnItemClickListener, View.OnClickListener {

    @InjectView(R.id.listView1)
	private ListView listView;
	@InjectView(R.id.img_folder_act_iv_back)
	private ImageView ivBack;
	@InjectView(R.id.img_folder_title)
	private TextView tvTitle;

	private ImgUtils util;
	private ImgFolderListAdapter listAdapter;
	private List<FileTraversal> locallist;
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_folder_list);
		Injector.get(this).inject();
		initData();
	}

	protected void initData() {
		ivBack.setOnClickListener(this);
		tvTitle.setText(getResources().getString(R.string.img_folder_title));
		util = new ImgUtils(this);
		locallist = util.LocalImgFileList();
		List<HashMap<String, String>> listdata = new ArrayList<>();
		if (locallist != null && locallist.size() > 0) {
			for (int i = 0; i < locallist.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				int size = locallist.get(i).filecontent.size();
				if(size > 0 ) {
					map.put("filecount", size + "张");
					map.put("imgpath", locallist.get(i).filecontent.get(0) == null ? null : (locallist.get(i).filecontent.get(0)));
					map.put("filename", locallist.get(i).filename);
					listdata.add(map);
				}
			}
		}
		listAdapter = new ImgFolderListAdapter(this, listdata);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this,ImgsUI.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("data", locallist.get(arg2));
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
//		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.img_folder_act_iv_back:
				finish();
				break;
		}
	}
}
