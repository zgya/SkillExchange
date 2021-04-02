package com.jit.zgy.skillexchange.bean;

public class Register {
    private String uphone;
    private String upasswrod;

    public Register(String uphone, String upasswrod) {
        this.uphone = uphone;
        this.upasswrod = upasswrod;
    }
    public Register(){

    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUpasswrod() {
        return upasswrod;
    }

    public void setUpasswrod(String upasswrod) {
        this.upasswrod = upasswrod;
    }
}
