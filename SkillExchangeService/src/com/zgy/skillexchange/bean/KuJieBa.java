package com.zgy.skillexchange.bean;

public class KuJieBa {
	private String filename;//语料库中的文本名字
	private String fspcontent;//语料库中每一个文档的分词
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFspcontent() {
		return fspcontent;
	}
	public void setFspcontent(String fspcontent) {
		this.fspcontent = fspcontent;
	}
	public KuJieBa(String filename, String fspcontent) {
		super();
		this.filename = filename;
		this.fspcontent = fspcontent;
	}
	public KuJieBa() {
		
	}
}
