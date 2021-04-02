package com.zgy.skillexchange.dao;

import java.util.ArrayList;

import com.zgy.skillexchange.bean.Daren;
import com.zgy.skillexchange.bean.DarenDetail;

public interface DarenDao {
	//展示达人。只有有技能标签的才能是达人，同时自己不会看到自己
	public ArrayList<Daren> ShowDaren(int uid);
	//根据用户id，在添加技能标签页面将达人的技能标签
	public void InsertDarenSkills(int uid,String uskills);
	//点击头像，进入对应的用户详情界面
	public DarenDetail ShowDarenDetails(int uid);
}
