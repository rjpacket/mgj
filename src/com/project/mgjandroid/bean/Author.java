package com.project.mgjandroid.bean;

import java.io.Serializable;

public class Author implements Serializable{
	
	private static final long serialVersionUID = 16758768L;
	private String name;
	private String headerImgUrl;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeaderImgUrl() {
		return headerImgUrl;
	}
	public void setHeaderImgUrl(String headerImgUrl) {
		this.headerImgUrl = headerImgUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
