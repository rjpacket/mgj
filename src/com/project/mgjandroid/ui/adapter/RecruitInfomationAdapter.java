package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RecruitBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RecruitModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.MyPublishInfoActivity;
import com.project.mgjandroid.ui.activity.RecruitInfomationActivity;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/6/22.
 *
 */
public class RecruitInfomationAdapter extends BaseListAdapter<RecruitBean> implements CustomDialog.onBtnClickListener {

    private SimpleDateFormat format = new SimpleDateFormat("发布时间：yyyy-MM-dd HH:mm");
    private boolean isMine;
    private CustomDialog dialog, stickyDialog;
    private HandleMoreSetLayoutListener listener;
    private CustomDialog mCustomDialog;
    private Long mId;
    private int mPosition;

    public RecruitInfomationAdapter(int layoutId, Activity mActivity, boolean isMine) {
        super(layoutId, mActivity);
        this.isMine = isMine;
        mCustomDialog = new CustomDialog(mActivity, this, "确定", "取消", "提示", "确定删除吗？");
    }

    public void setListener(HandleMoreSetLayoutListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, final RecruitBean bean, final int position, View convertView, ViewGroup parent) {
        TextView tvCategory = holder.getView(R.id.tv_category);
        TextView tvName = holder.getView(R.id.tv_name);
        ImageView ivSticky = holder.getView(R.id.iv_sticky);
        TextView tvSticky = holder.getView(R.id.tv_sticky_post);
        TextView tvPublishTime = holder.getView(R.id.tv_publish_time);
        TextView tvJobDuties = holder.getView(R.id.tv_job_duties);
        TextView tvJobDesc = holder.getView(R.id.tv_job_description);
        TextView tvMore = holder.getView(R.id.tv_more);
        ImageView ivMoreSet = holder.getView(R.id.iv_more_setting);
        final LinearLayout setLayout = holder.getView(R.id.setting_layout);
        TextView tvRefresh = holder.getView(R.id.tv_refresh);
        TextView tvStickyPost = holder.getView(R.id.tv_sticky);
        TextView tvDelete = holder.getView(R.id.tv_delete);
        setLayout.setVisibility(View.GONE);

        tvCategory.setText(bean.getPositionCategoryName());
        tvName.setText(bean.getName());
        if(bean.getIsTop() == 0) {
            if (isMine) {
                ivSticky.setVisibility(View.GONE);
                tvSticky.setVisibility(View.GONE);
                tvSticky.setTextColor(mActivity.getResources().getColor(R.color.red_job));
                tvSticky.setText("置顶");
            } else {
                ivSticky.setVisibility(View.GONE);
                tvSticky.setVisibility(View.GONE);
            }
        }else{
            if (isMine) {
                ivSticky.setVisibility(View.GONE);
                tvSticky.setVisibility(View.VISIBLE);
                tvSticky.setTextColor(mActivity.getResources().getColor(R.color.red_job));
                tvSticky.setText("已置顶");
            } else {
                ivSticky.setVisibility(View.INVISIBLE);
                tvSticky.setVisibility(View.VISIBLE);
                tvSticky.setTextColor(mActivity.getResources().getColor(R.color.red_job));
                tvSticky.setText("已置顶");
            }
        }
        tvPublishTime.setText(format.format(bean.getCreateTime()));
        tvJobDesc.setText(bean.getDescription());
        if (bean.getType() == 1) {
            tvJobDuties.setText("个人简介：");
        } else if (bean.getType() == 2){
            tvJobDuties.setText("职位描述：");
        }
        tvJobDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setLayout.getVisibility() == View.VISIBLE) {
                    setLayout.setVisibility(View.GONE);
                    return;
                }
                if (listener != null && listener.hide()) return;
                Intent intent = new Intent(mActivity, RecruitInfomationActivity.class);
                intent.putExtra("recruit_bean", bean);
                mActivity.startActivity(intent);
            }
        });
        tvJobDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setLayout.getVisibility() == View.VISIBLE) {
                    setLayout.setVisibility(View.GONE);
                    return;
                }
                if (listener != null && listener.hide()) return;
                Intent intent = new Intent(mActivity, RecruitInfomationActivity.class);
                intent.putExtra("recruit_bean", bean);
                mActivity.startActivity(intent);
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setLayout.getVisibility() == View.VISIBLE) {
                    setLayout.setVisibility(View.GONE);
                    return;
                }
                if (listener != null && listener.hide()) return;
                Intent intent = new Intent(mActivity, RecruitInfomationActivity.class);
                intent.putExtra("recruit_bean", bean);
                mActivity.startActivity(intent);
            }
        });
        if (isMine) {
            ivMoreSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(setLayout.getVisibility() == View.GONE){
                        if (listener != null && listener.hide()) return;
                        setLayout.setVisibility(View.VISIBLE);
                    } else {
                        setLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            ivMoreSet.setImageResource(R.drawable.icon_call);
            ivMoreSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(bean.getMobile());
                }
            });
        }
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout.setVisibility(View.GONE);
                refreshRecruitInfo(bean.getId(), position);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout.setVisibility(View.GONE);
                mCustomDialog.show();
                mId = bean.getId();
                mPosition = position;
            }
        });
        if(isMine && bean.getIsTop() == 0){
            tvSticky.setClickable(true);
            tvStickyPost.setClickable(true);
//            tvSticky.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(setLayout.getVisibility() == View.VISIBLE) {
//                        setLayout.setVisibility(View.GONE);
//                        return;
//                    }
//                    if (listener != null && listener.hide()) return;
//                    showStickyDialog();
//                }
//            });
            tvStickyPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLayout.setVisibility(View.GONE);
                    showStickyDialog();
                }
            });
        } else {
            tvSticky.setClickable(false);
//            tvStickyPost.setClickable(false);
            tvStickyPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLayout.setVisibility(View.GONE);
                    ToastUtils.displayMsg("该信息已置顶", mActivity);
                }
            });
        }
    }

    private void refreshRecruitInfo(Long id, final int position) {
        ((MyPublishInfoActivity) mActivity).loadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<RecruitModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_REFRESH_POSITION_RECRUIT_INFORMATION, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                ((MyPublishInfoActivity) mActivity).loadingDialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if(((RecruitModel) obj).getValue() != null){
                        RecruitInfomationAdapter.this.getData().remove(position);
                        RecruitInfomationAdapter.this.getData().add(0, ((RecruitModel) obj).getValue());
                        RecruitInfomationAdapter.this.notifyDataSetChanged();
                    }
                    ToastUtils.displayMsg("刷新成功", mActivity);
                }
            }
        }, RecruitModel.class);
    }

    private void deleteRecruitInfo(Long id, final int position) {
        ((MyPublishInfoActivity) mActivity).loadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", mId);
        VolleyOperater<RecruitModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DELETE_POSITION_RECRUIT_INFORMATION, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                ((MyPublishInfoActivity) mActivity).loadingDialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    RecruitInfomationAdapter.this.getData().remove(mPosition);
                    RecruitInfomationAdapter.this.notifyDataSetChanged();
                    ToastUtils.displayMsg(R.string.delete_success, mActivity);
                }
            }
        }, RecruitModel.class);
    }

    private void showDialog(final String mobile) {
        dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), mobile)));
                mActivity.startActivity(intent);
                dialog.dismiss();
            }
            @Override
            public void onExit() {
                dialog.dismiss();
            }
        },"呼叫","取消","联系电话",mobile);

        dialog.show();
    }

    private void showStickyDialog() {
        if (stickyDialog != null) {
            stickyDialog.show();
            return;
        }
        stickyDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), mActivity.getString(R.string.sale_phone))));
                mActivity.startActivity(intent);
                stickyDialog.dismiss();
            }
            @Override
            public void onExit() {
                stickyDialog.dismiss();
            }
        }, "呼叫", "取消", "置顶请拨打客服电话", mActivity.getString(R.string.sale_phone));

        stickyDialog.show();
    }

    @Override
    public void onSure() {
        mCustomDialog.dismiss();
        deleteRecruitInfo(mId, mPosition);
    }

    @Override
    public void onExit() {
        mCustomDialog.dismiss();
    }

    public interface HandleMoreSetLayoutListener {
        boolean hide();
    }
}