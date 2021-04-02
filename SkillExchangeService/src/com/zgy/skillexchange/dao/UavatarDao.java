package com.zgy.skillexchange.dao;

public interface UavatarDao {
	//登录时判断是否有自己得头像
	public String FindUavatarByUphone(String uphone);
	//修改用户头像时将头像存入数据库
	public void UpdateUavatar(String uphone,String uavatar);
	//根据uid寻找用户头像
	public String FindUavatarByUid(int uid);
}
