package com.jit.zgy.skillexchange.handler;

import android.util.Log;

import com.jit.zgy.skillexchange.bean.AddressBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class StringHandler extends DefaultHandler {
    private AddressBean addressBean;
    private String temp="";
    private String localName="";
    public AddressBean getParsedData() {
        return this.addressBean;
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //得到xml值
        String data=new String(ch,start,length);
        if(localName.endsWith("formatted_address")){
            temp=temp+data;
        }
    }
    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if(localName.endsWith("formatted_address")){
            addressBean.setFormatted_address(temp.trim());
            temp="";
        }
    }
    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        addressBean = new AddressBean();
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        this.localName=localName;
    }
}
