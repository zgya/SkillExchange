package com.jit.zgy.skillexchange.utils;

import java.util.HashMap;
import java.util.Map;

public class tfCalculateUtil {
    /**
     * 计算每个文档的tf值
     * @param wordAll
     * @return Map<String,Float> key是单词 value是tf值
     */
    public static Map<String,Float> tfCalculate(String wordAll){
        //存放（单词，单词数量）
        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        //存放（单词，单词词频）
        HashMap<String, Float> tf = new HashMap<String, Float>();
        int wordCount=0;

        /**
         * 统计每个单词的数量，并存放到map中去
         * 便于以后计算每个单词的词频
         * 单词的tf=该单词出现的数量n/总的单词数wordCount
         */
        for(String word:wordAll.split("\\s+")){
            wordCount++;
            if(dict.containsKey(word)){
                dict.put(word,  dict.get(word)+1);
            }else{
                dict.put(word, 1);
            }
        }

        for(Map.Entry<String, Integer> entry:dict.entrySet()){
            float wordTf=(float)entry.getValue()/wordCount;
            tf.put(entry.getKey(), wordTf);
        }
        return tf;
    }

}
