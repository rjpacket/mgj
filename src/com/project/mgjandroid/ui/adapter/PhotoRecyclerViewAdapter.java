package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.inter_face.ItemTouchHelperAdapter;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.ProgressImageView;
import com.project.mgjandroid.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yuandi on 2016/7/8.
 *
 */
public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {

    private ArrayList<UploadPhoto> uploadPhotos = new ArrayList<>();
    private AddPhotoClickListener listener;
    private Context mContext;
    private LayoutInflater inflater;
    private boolean isNeedAdd;
    private boolean canDelete = true;
    private final static int MAX_COUNT = ChoosePhotoModel.getInstance().getMaxCount();

    public PhotoRecyclerViewAdapter(Context context, ArrayList<UploadPhoto> uploadPhoto, boolean isNeedAdd) {
        mContext = context;
        uploadPhotos = uploadPhoto;
        inflater = LayoutInflater.from(context);
        this.isNeedAdd = isNeedAdd;
    }

    public void setListener(AddPhotoClickListener listener) {
        this.listener = listener;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (isNeedAdd) {
            return new PhotoViewHolder(inflater.inflate(R.layout.recycler_photo_view, viewGroup, false));
        } else{
            return new CameraViewHolder(inflater.inflate(R.layout.recycler_camera_view, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(isNeedAdd) {
            if (position == uploadPhotos.size()) {
                ((PhotoViewHolder) viewHolder).ivAdd.setVisibility(View.VISIBLE);
            } else {
                ((PhotoViewHolder) viewHolder).ivAdd.setVisibility(View.GONE);
                Bitmap bitmap = BitmapUtil.compressBitmap(uploadPhotos.get(position).getPath(), 480);
                ((PhotoViewHolder) viewHolder).photo.setImageBitmap(bitmap);
                if(uploadPhotos.get(position).isUploadFinish()) {
                    ((PhotoViewHolder) viewHolder).photo.setProgress(100);
                }else{
                    ((PhotoViewHolder) viewHolder).photo.setProgress(0);
                }
                if(uploadPhotos.get(position).isUploaded()){
                    ((PhotoViewHolder) viewHolder).tvUploadFailed.setVisibility(View.GONE);
                }else{
                    ((PhotoViewHolder) viewHolder).tvUploadFailed.setVisibility(View.VISIBLE);
                }
                if (position == 0) {
                    ((PhotoViewHolder) viewHolder).tvFirstPhoto.setVisibility(View.VISIBLE);
                } else {
                    ((PhotoViewHolder) viewHolder).tvFirstPhoto.setVisibility(View.GONE);
                }
            }
        } else {
            Bitmap bitmap = BitmapUtil.compressBitmap(uploadPhotos.get(position).getPath(), 480);
            ((CameraViewHolder) viewHolder).photo.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        if(isNeedAdd){
            if(uploadPhotos.size() < MAX_COUNT) return uploadPhotos.size() + 1;
        }
        return uploadPhotos.size();
    }

    public void setUploadPhotos(ArrayList<UploadPhoto> uploadPhotos) {
        this.uploadPhotos = uploadPhotos;
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if(fromPosition == uploadPhotos.size()){
            return;
        }
        if(toPosition == uploadPhotos.size()) {
            toPosition--;
        }
        Collections.swap(uploadPhotos, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        uploadPhotos.remove(position);
        notifyItemRemoved(position);
    }

    private class CameraViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private ImageView ivDel;

        public CameraViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.photo);
            ivDel = (ImageView) view.findViewById(R.id.iv_delete);
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPhotos.remove(getLayoutPosition());
                    PhotoRecyclerViewAdapter.this.notifyDataSetChanged();
                    if (listener != null){
                        listener.delPhoto();
                    }
                }
            });
        }
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ProgressImageView photo;
        private ImageView ivDel;
        private ImageView ivAdd;
        private TextView tvFirstPhoto;
        private TextView tvUploadFailed;

        public PhotoViewHolder(View view) {
            super(view);
            photo = (ProgressImageView) view.findViewById(R.id.photo);
            ivDel = (ImageView) view.findViewById(R.id.iv_delete);
            ivAdd = (ImageView) view.findViewById(R.id.add_photo);
            tvFirstPhoto = (TextView) view.findViewById(R.id.tv_first_photo);
            tvUploadFailed = (TextView) view.findViewById(R.id.tv_upload_fail);
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(canDelete){
                        ChoosePhotoModel.getInstance().updateCurrentPhotoList(uploadPhotos.get(getLayoutPosition()));
                        uploadPhotos.remove(getLayoutPosition());
                        PhotoRecyclerViewAdapter.this.notifyDataSetChanged();
                        if (listener != null){
                            listener.delPhoto();
                        }
                    }else{
                        ((BaseActivity) mContext).toast("正在上传照片，请稍后再进行操作");
                    }
                }
            });
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.addPhoto();
                    }
                }
            });
            tvUploadFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.uploadPhoto(getLayoutPosition());
                        tvUploadFailed.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public interface AddPhotoClickListener{
        void delPhoto();
        void addPhoto();
        void uploadPhoto(int position);
    }
}
