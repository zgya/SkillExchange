package com.jit.zgy.skillexchange.bean;

public class TaskOthersBean {
    //任务列表剩余的内容：包括谁发布了这个任务，用户此时的地理位置，用户的头像
    int uid;
    private String uavatar;
    private String ulocation;

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

    public String getUlocation() {
        return ulocation;
    }

    public void setUlocation(String ulocation) {
        this.ulocation = ulocation;
    }

    public TaskOthersBean(int uid, String uavatar) {
        this.uid = uid;
        this.uavatar = uavatar;
    }

    public TaskOthersBean(int uid, String uavatar, String ulocation) {
        this.uid = uid;
        this.uavatar = uavatar;
        this.ulocation = ulocation;
    }

    public TaskOthersBean(){

    }

}
