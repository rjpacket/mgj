package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CommercialComment implements Serializable{
	private static final long serialVersionUID = 657561L;
	private Author author;
	private float rate;
	private String description;
	private String createTime;
	private String receiptTime;
	private ArrayList <LeafComment>leafComments;
	private boolean isChecked;
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	public ArrayList<LeafComment> getLeafComments() {
		return leafComments;
	}
	public void setLeafComments(ArrayList<LeafComment> leafComments) {
		this.leafComments = leafComments;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	

}
