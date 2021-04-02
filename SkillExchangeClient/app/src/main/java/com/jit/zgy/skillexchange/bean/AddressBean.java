package com.jit.zgy.skillexchange.bean;

public class AddressBean {
    private String formatted_address;
    public AddressBean(){}

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public AddressBean(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
