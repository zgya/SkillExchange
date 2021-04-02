package com.jit.zgy.skillexchange.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tfidfCalculateUtil {
    /**
     *
     * @param D 总文档数
     * @param doc_words 每个文档对应的分词
     * @param tf 计算好的tf,用这个作为基础计算tfidf
     * @return 每个文档中的单词的tfidf的值
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static Map<String,Float> tfidfCalculate(int D, Map<String,String> doc_words, Map<String,Float> tf) throws FileNotFoundException, IOException {

        HashMap<String,Float> tfidf=new HashMap<String, Float>();
        for(String key:tf.keySet()){
            int Dt=0;
            for(Map.Entry<String, String> entry:doc_words.entrySet()){

                String[] words=entry.getValue().split("\\s+");

                List<String> wordlist=new ArrayList<String>();
                for(int i=0;i<words.length;i++){
                    wordlist.add(words[i]);
                }
                if(wordlist.contains(key)){
                    Dt++;
                }
            }
            float idfvalue=(float) Math.log(Float.valueOf(D)/(Dt+1));
            tfidf.put(key, idfvalue * tf.get(key));
        }
        return tfidf;
    }

}
