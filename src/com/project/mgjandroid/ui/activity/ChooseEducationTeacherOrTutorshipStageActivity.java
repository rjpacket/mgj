package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationTeacherTypeOrTutorshipStage;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.EduChooseListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.EduChooseAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class ChooseEducationTeacherOrTutorshipStageActivity extends BaseActivity{

    public final static String TYPE_TEACHER_TYPE = "teacher_type";
    public final static String TYPE_TUTORSHIP_STAGE = "tutorship_stage";
    public final static String ID = "id";
    public final static String NAME = "name";

    @InjectView(R.id.list_view)
    private ListView listView;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;

    private EduChooseAdapter adapter;
    private MLoadingDialog mMLoadingDialog;
    private String type;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_choose);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("type")){
            type = intent.getStringExtra("type");
        }
        if(TYPE_TEACHER_TYPE.equals(type)){
            url = Constants.URL_FIND_EDUCATION_TEACHER_TYPE;
            tvTitle.setText("教师身份");
        }else if(TYPE_TUTORSHIP_STAGE.equals(type)){
            url = Constants.URL_FIND_EDUCATION_TUTORSHIP_STAGE;
            tvTitle.setText("辅导阶段");
        }else{
            finish();
        }

        adapter = new EduChooseAdapter(R.layout.item_textview, mActivity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EducationTeacherTypeOrTutorshipStage bean = (EducationTeacherTypeOrTutorshipStage) listView.getAdapter().getItem(position);
                Intent intent1 = new Intent();
                intent1.putExtra(ID, bean.getId());
                intent1.putExtra(NAME, bean.getName());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        ivBack.setOnClickListener(this);
        mMLoadingDialog = new MLoadingDialog();

        getData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 获取数据
     */
    public void getData() {
        mMLoadingDialog.show(getFragmentManager(),"");
        VolleyOperater<EduChooseListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(url, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    EduChooseListModel model = (EduChooseListModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        adapter.setData(model.getValue());
                    }
                }
            }
        }, EduChooseListModel.class);
    }
}
