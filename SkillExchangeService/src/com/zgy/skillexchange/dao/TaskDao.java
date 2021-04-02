package com.zgy.skillexchange.dao;

import java.util.ArrayList;

import com.zgy.skillexchange.bean.Task;

public interface TaskDao {
	public int[] FindTidByUid(int uid);//找到用户对应发布的任务的id(这个用户发布的任务不应该出现在首页的任务列表中)
	public ArrayList<Task> ShowTask(int tid[]);
	public Task FindTaskByTid(int tid);//任务详情
	public void insertTask(Task task);//增加一条任务
}
