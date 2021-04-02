package com.zgy.skillexchange.bean;

public class Daren {
	private int uid;//达人id
	private String uavatar;//达人头像
	private String uname;//达人用户名
	private int enumbers;//多少条评论
	private String ucertification;//达人是否认证
	private String uskills;//达人技能标签
	private float ustars;//达人完成任务后获得的评分（平均）
	private int uprice;//达人报价，为0则是沟通后报价
	private String udescription;//达人自我介绍
	public String getUavatar() {
		return uavatar;
	}
	public void setUavatar(String uavatar) {
		this.uavatar = uavatar;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getEnumbers() {
		return enumbers;
	}
	public void setEnumbers(int enumbers) {
		this.enumbers = enumbers;
	}
	public String getUcertification() {
		return ucertification;
	}
	public void setUcertification(String ucertification) {
		this.ucertification = ucertification;
	}
	public String getUskills() {
		return uskills;
	}
	public void setUskills(String uskills) {
		this.uskills = uskills;
	}
	public float getUstars() {
		return ustars;
	}
	public void setUstars(float ustars) {
		this.ustars = ustars;
	}
	public int getUprice() {
		return uprice;
	}
	public void setUprice(int uprice) {
		this.uprice = uprice;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUdescription() {
		return udescription;
	}
	public void setUdescription(String udescription) {
		this.udescription = udescription;
	}
	public Daren() {
		
	}
	public Daren(int uid, String uavatar, String uname, int enumbers, String ucertification, String uskills,
			float ustars, int uprice, String udescription) {
		super();
		this.uid = uid;
		this.uavatar = uavatar;
		this.uname = uname;
		this.enumbers = enumbers;
		this.ucertification = ucertification;
		this.uskills = uskills;
		this.ustars = ustars;
		this.uprice = uprice;
		this.udescription = udescription;
	}
	
	
}
