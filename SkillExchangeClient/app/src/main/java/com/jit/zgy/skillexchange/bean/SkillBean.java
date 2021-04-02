package com.jit.zgy.skillexchange.bean;

import java.util.List;

public class SkillBean {

    /**
     * SCategory : 北京
     * skills : ["北京"]
     */

    private String SCategory;
    private List<String> skills;

    public String getSCategory() {
        return SCategory;
    }

    public void setSCategory(String SCategory) {
        this.SCategory = SCategory;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getPickerViewText() {
        return this.SCategory;
    }
}
