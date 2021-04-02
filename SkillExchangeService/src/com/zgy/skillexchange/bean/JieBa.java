package com.zgy.skillexchange.bean;

public class JieBa {
	private int tid;
	private String tspcontent;
	public JieBa(int tid, String tspcontent) {
		super();
		this.tid = tid;
		this.tspcontent = tspcontent;
	}
	public JieBa() {
		
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTspcontent() {
		return tspcontent;
	}
	public void setTspcontent(String tspcontent) {
		this.tspcontent = tspcontent;
	}
	
}
