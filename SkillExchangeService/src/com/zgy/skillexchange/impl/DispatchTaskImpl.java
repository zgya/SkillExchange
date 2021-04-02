package com.zgy.skillexchange.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.zgy.skillexchange.dao.DispatchTaskDao;
import com.zgy.skillexchange.util.DBUtils;

public class DispatchTaskImpl implements DispatchTaskDao{
	@Override
	public int FindUidByTid(int tid) {
		int uid = 0; 
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement(); 
			String sql = "select uid from dispatchtask where tid ="+tid;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				uid = rs.getInt("uid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uid;
	}

}
