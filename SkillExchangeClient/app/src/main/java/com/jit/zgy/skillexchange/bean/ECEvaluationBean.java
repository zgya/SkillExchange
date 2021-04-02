package com.jit.zgy.skillexchange.bean;

public class ECEvaluationBean {
    private String dc;//是达人还是用户
    private String uavatar;//评价的人的头像
    private String uname;//评价的人的名字
    private String comment;//评价内容
    private Float estars;//星级
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
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getDc() {
        return dc;
    }
    public void setDc(String dc) {
        this.dc = dc;
    }
    public Float getEstars() {
        return estars;
    }
    public void setEstars(Float estars) {
        this.estars = estars;
    }

    public ECEvaluationBean(String dc, String uavatar, String uname, String comment, Float estars) {
        this.dc = dc;
        this.uavatar = uavatar;
        this.uname = uname;
        this.comment = comment;
        this.estars = estars;
    }

    public ECEvaluationBean() {}

    public ECEvaluationBean(String uavatar, String uname, String comment) {
        this.uavatar = uavatar;
        this.uname = uname;
        this.comment = comment;
    }
}
