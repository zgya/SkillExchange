package com.zgy.skillexchange.bean;

import java.util.Date;

public class Task {
	private int uid;//谁发布了任务
	private String uavatar;//发布任务的人的头像
	private String uname;//发布的人的名字
	private int tid;//任务的id
	private String ttitle;
	private String tcontent;
	private String taddress;
	private float tprice;
	private String tdeadlineDate;
	private int tbids;
	private int tstate;//任务状态 0-招标中，1-已中标，2-已过期
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTtitle() {
		return ttitle;
	}
	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}
	public String getTcontent() {
		return tcontent;
	}
	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}
	public float getTprice() {
		return tprice;
	}
	public void setTprice(float tprice) {
		this.tprice = tprice;
	}
	public String getTdeadlineDate() {
		return tdeadlineDate;
	}
	public void setTdeadlineDate(String tdeadlineDate) {
		this.tdeadlineDate = tdeadlineDate;
	}
	public int getTbids() {
		return tbids;
	}
	public void setTbids(int tbids) {
		this.tbids = tbids;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
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
	public int getTstate() {
		return tstate;
	}
	public void setTstate(int tstate) {
		this.tstate = tstate;
	}
	
	public String getTaddress() {
		return taddress;
	}
	public void setTaddress(String taddress) {
		this.taddress = taddress;
	}	
	
	public Task(int uid, String uavatar, String uname, int tid, String ttitle, String tcontent, String taddress,
			float tprice, String tdeadlineDate, int tbids, int tstate) {
		super();
		this.uid = uid;
		this.uavatar = uavatar;
		this.uname = uname;
		this.tid = tid;
		this.ttitle = ttitle;
		this.tcontent = tcontent;
		this.taddress = taddress;
		this.tprice = tprice;
		this.tdeadlineDate = tdeadlineDate;
		this.tbids = tbids;
		this.tstate = tstate;
	}
	public Task(String uavatar, String uname, String ttitle, String tcontent, float tprice, String tdeadlineDate) {
		super();
		this.uavatar = uavatar;
		this.uname = uname;
		this.ttitle = ttitle;
		this.tcontent = tcontent;
		this.tprice = tprice;
		this.tdeadlineDate = tdeadlineDate;
	}
	public Task() {
		super();
	}
	

}
