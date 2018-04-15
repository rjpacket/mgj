package com.project.mgjandroid.bean;

import java.io.Serializable;

public class UserAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/** 用户编号 */
	private Long userId;
	/** 地址 */
	private String address = "";
	/** 门牌楼号 */
	private String houseNumber = "";
	/** 姓名 */
	private String name = "";
	/** 性别 */
	private String gender = "";

	private String mobile = "";
	/** 备选电话 */
	private String backupMobile = "";
	/** 经度 */
	private Double longitude;
	/** 维度 */
	private Double latitude;
	/** GeoHash */
	private String geohash;
	/** 是否删除 */
	private int hasDel;

	private int distance;

	private int overShipping;

	private String overShippingTxt = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBackupMobile() {
		return backupMobile;
	}

	public void setBackupMobile(String backupMobile) {
		this.backupMobile = backupMobile;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getGeohash() {
		return geohash;
	}

	public void setGeohash(String geohash) {
		this.geohash = geohash;
	}

	public int getHasDel() {
		return hasDel;
	}

	public void setHasDel(int hasDel) {
		this.hasDel = hasDel;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getOverShipping() {
		return overShipping;
	}

	public void setOverShipping(int overShipping) {
		this.overShipping = overShipping;
	}

	public String getOverShippingTxt() {
		return overShippingTxt;
	}

	public void setOverShippingTxt(String overShippingTxt) {
		this.overShippingTxt = overShippingTxt;
	}

}