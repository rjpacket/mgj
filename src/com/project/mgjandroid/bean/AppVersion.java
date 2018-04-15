package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

public class AppVersion extends Entity {

	private static final long serialVersionUID = 1L;
	private Long id;
	/** 版本名 **/
	private String name;
	/** 渠道编号 */
	private String channel;
	/** 应用名称 */
	private String appName;
	/** 客户端类型 android,iphone等 **/
	private String client;
	/** 客户端版本号 **/
	private String clientVersion;
	/** 下载地址 **/
	private String downloadUrl;
	/** 是否强制更新： 0：否，1：是 **/
	private int isCoerceUpdate;
	/** 是否可用 0：不可用，1：可用 **/
	private int isAble;
	/** 版本描述 **/
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public int getIsCoerceUpdate() {
		return isCoerceUpdate;
	}

	public void setIsCoerceUpdate(int isCoerceUpdate) {
		this.isCoerceUpdate = isCoerceUpdate;
	}

	public int getIsAble() {
		return isAble;
	}

	public void setIsAble(int isAble) {
		this.isAble = isAble;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}