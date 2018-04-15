package com.project.mgjandroid.bean;

import java.io.Serializable;

public class ParametersDefinition implements Serializable{

	private static final long serialVersionUID = 1121313L;
	private String description;
	private String name;
	private String displayName;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	

}
