package com.project.mgjandroid.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.project.mgjandroid.bean.CommercialComment;

public class CommercialCommentModel implements Serializable {
	private static final long serialVersionUID = 165674L;
	private int code;
	private String uuid;
	private ArrayList<CommercialComment> value;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ArrayList<CommercialComment> getValue() {
		return value;
	}
	public void setValue(ArrayList<CommercialComment> value) {
		this.value = value;
	}

}
