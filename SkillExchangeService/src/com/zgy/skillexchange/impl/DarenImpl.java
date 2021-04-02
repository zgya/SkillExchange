package com.zgy.skillexchange.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.zgy.skillexchange.bean.Daren;
import com.zgy.skillexchange.bean.DarenDetail;
import com.zgy.skillexchange.dao.DarenDao;
import com.zgy.skillexchange.dao.UavatarDao;
import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.util.DBUtils;

public class DarenImpl implements DarenDao{
	public ArrayList<Daren> ShowDaren(int uid) {
		ArrayList<Daren> DarenList = new ArrayList<Daren>();
		float astars = 0;//平均值
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select * from userinfo where uid != '"+uid+"' and uskills is not null";
			String sqlString = "select * from evaluation";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int eid = 0;//达人多少条评论
				float stars = 0;//stars的记录
				
				Daren daren = new Daren();
				daren.setUid(rs.getInt("uid"));
				daren.setUavatar(rs.getString("uavatar"));
				daren.setUname(rs.getString("uname"));
				daren.setUcertification(rs.getString("ucertification"));
				daren.setUskills(rs.getString("uskills"));
				daren.setUprice(rs.getInt("uprice"));
				daren.setUdescription(rs.getString("udescription"));
				int darenid = rs.getInt("uid");
				//evaluation表
				Statement statement=con.createStatement();  
				ResultSet rse = statement.executeQuery(sqlString);
				while(rse.next()) {
					if(rse.getInt("daren") == darenid) {
						eid++;
						stars += rse.getFloat("stars");
					}
				}
				//如果有技能标签，但是还没有接受任务，此时eid=0,stars=0，做特殊处理
				if(eid == 0) {
					daren.setEnumbers(0);
					daren.setUstars(0);
				}else {
					astars = stars/eid;
					daren.setEnumbers(eid);
					daren.setUstars(astars);
				}
				DarenList.add(daren);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DarenList;
		
	}

	@Override
	public void InsertDarenSkills(int uid, String uskills) {
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement(); 
			String sql = "update userinfo set uskills = '"+uskills+"' where uid = "+uid;
			stmt.execute(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DarenDetail ShowDarenDetails(int uid) {
		Connection con=null;
		DarenDetail darenDetail = new DarenDetail();
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select * from userinfo where uid ="+uid;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				darenDetail.setUid(uid);
				darenDetail.setUavatar(rs.getString("uavatar"));
				darenDetail.setUname(rs.getString("uname"));
				darenDetail.setUdescription(rs.getString("udescription"));
				darenDetail.setUskills(rs.getString("uskills"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return darenDetail;
		
	}
	
	
		
}
