package com.zgy.skillexchange.dao;

import java.util.ArrayList;

import com.zgy.skillexchange.bean.ECEvaluation;

public interface EvaluationDao {
	public ArrayList<ECEvaluation> ShowEvaluation(int uid);//展示用户（达人和用户）的评论
	public ArrayList<ECEvaluation> ShowTaskEvaluation(int tid);//根据tid展示任务评论(只有已过期，即任务完成后才有评论)
	public ArrayList<ECEvaluation> ShowMyEvaluation(int uid);//不区分达人和用户
}
