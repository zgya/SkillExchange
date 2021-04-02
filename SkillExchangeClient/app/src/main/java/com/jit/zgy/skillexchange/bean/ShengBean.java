package com.jit.zgy.skillexchange.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class ShengBean implements IPickerViewData {
    public String name;
    public List<Shi> city;
    public static class Shi{
        public String name;
        public List<String>area;

    }
    //  这个要返回省的名字
    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
