package com.jit.zgy.skillexchange.bean;

public class SettingsBean {
    private int image1;
    private String text1;
    private int image2;

    public SettingsBean(int image1, String text1, int image2) {
        this.image1 = image1;
        this.text1 = text1;
        this.image2 = image2;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }
}
