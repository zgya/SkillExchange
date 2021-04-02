package com.zgy.skillexchange.bean;

public class DispatchTask {
	private int uid;//谁发布了任务
	private int tid;//发布了什任务
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public DispatchTask(int uid, int tid) {
		super();
		this.uid = uid;
		this.tid = tid;
	}
	
	public DispatchTask() {
		
	}
}
