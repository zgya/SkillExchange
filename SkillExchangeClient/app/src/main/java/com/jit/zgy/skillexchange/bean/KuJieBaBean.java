package com.jit.zgy.skillexchange.bean;

public class KuJieBaBean {
    private String filename;//语料库中的文本名字
    private String fspcontent;//语料库中每一个文档的分词

    public KuJieBaBean(String filename, String fspcontent) {
        this.filename = filename;
        this.fspcontent = fspcontent;
    }

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

    public KuJieBaBean(){

    }
}
