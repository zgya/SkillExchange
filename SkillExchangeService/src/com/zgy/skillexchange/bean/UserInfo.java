package com.zgy.skillexchange.bean;

public class UserInfo {
	private int uid;
	private String uphone;
	private String upassword;
	private String uname;
	private String uavatar;
	
	public String getUname() {
		return uname;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUavatar() {
		return uavatar;
	}
	public void setUavatar(String uavatar) {
		this.uavatar = uavatar;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	
}
