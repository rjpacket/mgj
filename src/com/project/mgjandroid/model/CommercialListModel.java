package com.project.mgjandroid.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.project.mgjandroid.bean.Merchant;

/**
 * 商户列表
 * @author jian
 *
 */
public class CommercialListModel implements Serializable{

	private static final long serialVersionUID = 1111111L;
	private int code;
	private String uuid;
	private ArrayList<Merchant> value;
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
	public ArrayList<Merchant> getValue() {
		return value;
	}
	public void setValue(ArrayList<Merchant> value) {
		this.value = value;
	}

}
