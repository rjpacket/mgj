package com.project.mgjandroid.chooseimage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class ImgUtils {

    Context context;

    public ImgUtils(Context context) {
        this.context = context;
    }

    public ArrayList<String> listAlldir() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        ArrayList<String> list = new ArrayList<String>();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                list.add(new File(path).getAbsolutePath());
            }
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<FileTraversal> LocalImgFileList() {
        List<FileTraversal> data = new ArrayList<FileTraversal>();
        String filename = "";
        List<String> allimglist = listAlldir();
        List<String> retulist = new ArrayList<String>();
        if (allimglist != null) {
            Set set = new TreeSet();
            String[] str;
            for (int i = 0; i < allimglist.size(); i++) {
                retulist.add(getFileInfo(allimglist.get(i)));
            }
            for (int i = 0; i < retulist.size(); i++) {
                set.add(retulist.get(i));
            }
            str = (String[]) set.toArray(new String[0]);
            for (int i = 0; i < str.length; i++) {
                filename = str[i];
                FileTraversal ftl = new FileTraversal();
                ftl.filename = filename;
                data.add(ftl);
            }

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < allimglist.size(); j++) {
                    if (data.get(i).filename.equals(getFileInfo(allimglist.get(j)))) {
                        File file = new File(allimglist.get(j));
                        if(file.exists() && file.length() > 0){
                            data.get(i).filecontent.add(allimglist.get(j));
                        }
                    }
                }
            }
        }
        return data;
    }

    public Bitmap getPathBitmap(Uri imageFilePath, int dw, int dh) throws FileNotFoundException {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Bitmap pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath), null, op);

        int wRatio = (int) Math.ceil(op.outWidth / (float) dw);
        int hRatio = (int) Math.ceil(op.outHeight / (float) dh);

        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        try {
            pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath), null, op);
        } catch (FileNotFoundException | OutOfMemoryError e) {
            e.printStackTrace();
        }

        return pic;
    }

    public String getFileInfo(String data) {
        String[] filename = data.split("/");
        if (filename != null) {
            return filename[filename.length - 2];
        }
        return null;
    }

    public void imgExcute(ImageView imageView, ImgCallBack icb, String... params) {
        LoadBitAsync loadBitAsynk = new LoadBitAsync(imageView, icb);
        loadBitAsynk.execute(params);
    }

    public class LoadBitAsync extends AsyncTask<String, Integer, Bitmap> {

        ImageView imageView;
        ImgCallBack icb;

        LoadBitAsync(ImageView imageView, ImgCallBack icb) {
            this.imageView = imageView;
            this.icb = icb;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        bitmap = getPathBitmap(Uri.fromFile(new File(params[i])), 200, 200);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                icb.resultImgCall(imageView, result);
            }
        }
    }
}
