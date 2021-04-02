package com.zgy.skillexchange.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.zgy.skillexchange.dao.UavatarDao;
import com.zgy.skillexchange.util.DBUtils;

public class UavatarImpl implements UavatarDao{
	//
	public String FindUavatarByUphone(String uphone) {
		String uavatar = "avatar";
		
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select uavatar from userinfo where uphone ="+uphone;
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				uavatar = rs.getString("uavatar");
			}
	
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return uavatar;
	}
	
	//
	public void UpdateUavatar(String uphone,String uavatar) {
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			String sql = "update userinfo set uavatar ='"+uavatar+"' where uphone ="+uphone+"";
			Statement stmt=con.createStatement();  
			stmt.execute(sql);
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}

	@Override
	public String FindUavatarByUid(int uid) {
		String uavatar = "avatar";
		Connection con = null;
		try {
			con = DBUtils.getConnection();
			String sql = "select uavatar from userinfo where uid ="+uid;
			Statement stmt=con.createStatement(); 
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				uavatar = rs.getString("uavatar");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uavatar;
	}
	
	

}
