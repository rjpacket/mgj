package com.project.mgjandroid.chooseimage;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ParcelCreator")
public class FileTraversal implements Parcelable {

	public String filename;

	public List<String> filecontent = new ArrayList<>();

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(filename);
		dest.writeList(filecontent);
	}

	public static final Creator<FileTraversal> CREATOR = new Creator<FileTraversal>() {
		@Override
		public FileTraversal[] newArray(int size) {
			return null;
		}
		@SuppressWarnings("unchecked")
		@Override
		public FileTraversal createFromParcel(Parcel source) {
			FileTraversal ft = new FileTraversal();
			ft.filename = source.readString();
			ft.filecontent = source.readArrayList(FileTraversal.class.getClassLoader());
			return ft;
		}
	};
}
