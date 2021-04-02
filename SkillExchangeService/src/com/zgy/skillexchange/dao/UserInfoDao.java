package com.zgy.skillexchange.dao;

import com.zgy.skillexchange.bean.UserInfo;

public interface UserInfoDao {
	public String FindUnameByUphone(String uphone);
	public void UpdateUname(String uphone,String uname);
	public String FindUnameByUid(int uid);
	public void Insertuserinfo(UserInfo userInfo);//新用户注册
	
}
