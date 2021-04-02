package com.zgy.skillexchange.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zgy.skillexchange.bean.JieBa;
import com.zgy.skillexchange.bean.KuJieBa;
import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.JieBaDao;
import com.zgy.skillexchange.util.SearchFile;

import jieba.JiebaSegmenter;

public class JieBaImpl implements JieBaDao {
	//最终返回的是所有展示任务的...
	@Override
	public ArrayList<JieBa> JieBaFunc(ArrayList<Task> tasks) {
		ArrayList<JieBa> jieBas = new ArrayList<JieBa>();
		int i;
		for(i = 0; i < tasks.size(); i++) {
			JieBa jieBa = new JieBa();
			jieBa.setTid(tasks.get(i).getTid());
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(tasks.get(i).getTtitle()).append(tasks.get(i).getTcontent());
			String content = stringBuilder.toString();
			//去掉特殊字符
			String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(content);
			String newString = m.replaceAll("").trim();
			//jieba分词
			JiebaSegmenter segmenter = new JiebaSegmenter();
			List<String> tspcontentList = segmenter.sentenceProcess(newString);
			String spcontent = String.join(" ",tspcontentList);
			jieBa.setTspcontent(spcontent);
			jieBas.add(jieBa);
		}
		return jieBas;
	}

	//语料库分词
	@Override
	public ArrayList<KuJieBa> KuJieBaFunc() throws IOException{
		File f = new File("C:\\Users\\Lenovo\\Desktop\\cankao\\Reduced-fenci\\Reduced-fenci");
		List<String> list = new ArrayList<String>();
		list = SearchFile.getFileList(f);
		ArrayList<KuJieBa> kuJieBas = new ArrayList<KuJieBa>();
		for (String l : list) {
			KuJieBa kuJieBa = new KuJieBa();
			BufferedReader br = new BufferedReader(new FileReader(new File(l)));
			String line = br.readLine();
			byte[]b=line.getBytes("utf-8");
			String content=new String(b, "utf-8");
			kuJieBa.setFilename(l);
			kuJieBa.setFspcontent(content);
			kuJieBas.add(kuJieBa);
		}
		return kuJieBas;
	}
	

}
