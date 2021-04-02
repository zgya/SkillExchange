package com.jit.zgy.skillexchange.bean;

public class DarenDetailsBean {
    private int uid;//达人id
    private String uname;//达人名字
    private String uavatar;//达人头像
    private String udescription;//达人自我介绍
    private String uskills;//达人技能标签
    public DarenDetailsBean(int uid, String uname, String uavatar, String udescription, String uskills) {
        super();
        this.uid = uid;
        this.uname = uname;
        this.uavatar = uavatar;
        this.udescription = udescription;
        this.uskills = uskills;
    }
    public DarenDetailsBean() {}
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUname() {
        return uname;
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
    public String getUdescription() {
        return udescription;
    }
    public void setUdescription(String udescription) {
        this.udescription = udescription;
    }
    public String getUskills() {
        return uskills;
    }
    public void setUskills(String uskills) {
        this.uskills = uskills;
    }

}
