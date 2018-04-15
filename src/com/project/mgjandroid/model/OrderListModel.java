package com.project.mgjandroid.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.project.mgjandroid.bean.Order;

public class OrderListModel implements Serializable{

	private static final long serialVersionUID = 1123124L;
	private int code;
	private String uuid;
	private ArrayList<Order> value;
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
	public ArrayList<Order> getValue() {
		return value;
	}
	public void setValue(ArrayList<Order> value) {
		this.value = value;
	}
	

}
