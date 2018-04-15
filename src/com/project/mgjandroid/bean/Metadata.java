package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Metadata implements Serializable{

	
	 
	private static final long serialVersionUID = 332321L;
	private String id;
	private String name;
	private String img;
	private boolean supportMultiExpression;
	private boolean canMerge;
	private boolean campagin;
	private ArrayList<ParametersDefinition>parametersDefinition;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isSupportMultiExpression() {
		return supportMultiExpression;
	}
	public void setSupportMultiExpression(boolean supportMultiExpression) {
		this.supportMultiExpression = supportMultiExpression;
	}
	public boolean isCanMerge() {
		return canMerge;
	}
	public void setCanMerge(boolean canMerge) {
		this.canMerge = canMerge;
	}
	public boolean isCampagin() {
		return campagin;
	}
	public void setCampagin(boolean campagin) {
		this.campagin = campagin;
	}
	public ArrayList<ParametersDefinition> getParametersDefinition() {
		return parametersDefinition;
	}
	public void setParametersDefinition(ArrayList<ParametersDefinition> parametersDefinition) {
		this.parametersDefinition = parametersDefinition;
	}
	

}
