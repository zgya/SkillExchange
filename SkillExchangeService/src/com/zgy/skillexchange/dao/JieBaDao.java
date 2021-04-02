package com.zgy.skillexchange.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zgy.skillexchange.bean.JieBa;
import com.zgy.skillexchange.bean.KuJieBa;
import com.zgy.skillexchange.bean.Task;

public interface JieBaDao {
	//jieba分词
	public ArrayList<JieBa> JieBaFunc(ArrayList<Task> tasks);
	//语料库分词
	public ArrayList<KuJieBa> KuJieBaFunc() throws IOException;
}
