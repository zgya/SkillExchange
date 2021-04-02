package com.jit.zgy.skillexchange.bean;

import java.util.HashMap;
import java.util.Map;

public class TFIDFBean {
    private int tid;
    private Map<String,Float> tf;
    private Map<String,Float> idf;
    private Map<String,Float> tfidf;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public Map<String, Float> getTf() {
        return tf;
    }

    public void setTf(Map<String, Float> tf) {
        this.tf = tf;
    }

    public Map<String, Float> getIdf() {
        return idf;
    }

    public void setIdf(Map<String, Float> idf) {
        this.idf = idf;
    }

    public Map<String, Float> getTfidf() {
        return tfidf;
    }

    public void setTfidf(Map<String, Float> tfidf) {
        this.tfidf = tfidf;
    }

    public TFIDFBean(int tid, Map<String, Float> tf, Map<String, Float> idf, Map<String, Float> tfidf) {
        this.tid = tid;
        this.tf = tf;
        this.idf = idf;
        this.tfidf = tfidf;
    }

    public TFIDFBean(){

    }

}
