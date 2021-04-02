package com.zgy.skillexchange.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.zgy.skillexchange.bean.UserInfo;
import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.util.DBUtils;

public class UserInfoImpl implements UserInfoDao{
	//查询用户是否有名字
	public String FindUnameByUphone(String uphone) {
		String uname = null;
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select uname from userinfo where uphone = "+uphone;
			stmt.execute(sql);
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				uname = rs.getString("uname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uname;
	}
	
	@Override
	public String FindUnameByUid(int uid) {
		String uname = null;
		Connection con = null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select uname from userinfo where uid ="+uid;
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				uname = rs.getString("uname");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uname;
	}

	//修改用户名字
	public void UpdateUname(String uphone,String uname) {
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "update userinfo set uname ='"+uname+"' where uphone ="+uphone+"";
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Insertuserinfo(UserInfo userInfo) {
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement(); 	
			String uphone = userInfo.getUphone();
			String upassword = userInfo.getUpassword();
			String sql = "insert into userinfo(uphone,upassword) values('"+uphone+"','"+upassword+"')";
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
}
