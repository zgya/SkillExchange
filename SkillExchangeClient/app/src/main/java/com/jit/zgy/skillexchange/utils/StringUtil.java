package com.jit.zgy.skillexchange.utils;

public class StringUtil {
    //删除指定字符
    public static StringBuffer deleteSubString(StringBuffer sb,String substring) {
        while(sb.indexOf(substring)!=-1)
            sb.delete(sb.indexOf(substring),sb.indexOf(substring)+substring.length()+1);
        return sb;
    }
    //删除最后一个字符
    public static StringBuffer deleteLastString(StringBuffer sb){
        sb.deleteCharAt(sb.length()-1);
        return sb;
    }
}
