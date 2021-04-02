package com.zgy.skillexchange.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zgy.skillexchange.bean.ECEvaluation;
import com.zgy.skillexchange.dao.EvaluationDao;
import com.zgy.skillexchange.dao.UavatarDao;
import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.util.DBUtils;

public class EvaluationImpl implements EvaluationDao{

	//区分角色
	@Override
	public ArrayList<ECEvaluation> ShowEvaluation(int uid) {
		Connection con=null;
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		try {
			con=DBUtils.getConnection();
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			String sqlu = "select * from evaluation where customn ="+uid;
			String sqle = "select * from evaluation where daren = "+uid;
			ResultSet rs1 = stmt1.executeQuery(sqlu);
			ResultSet rs2 = stmt2.executeQuery(sqle);
			//作为达人收到来自用户的评价
			while(rs2.next()) {
				ECEvaluation EcEvaluation = new ECEvaluation();
				UserInfoDao userInfoDao = new UserInfoImpl();
				UavatarDao uavatarDao = new UavatarImpl();
				int customnid = rs2.getInt("customn");
				String customnname = userInfoDao.FindUnameByUid(customnid);
				String customnavatar = uavatarDao.FindUavatarByUid(customnid);
				String customncomment = rs2.getString("ucomment");
				EcEvaluation.setUname(customnname);
				EcEvaluation.setUavatar(customnavatar);
				EcEvaluation.setComment(customncomment);
				EcEvaluation.setDc("daren");//达人角色
				evaluationlist.add(EcEvaluation);
			}
			
			//作为用户收到来自达人的评价
			while(rs1.next()) {
				ECEvaluation EcEvaluation = new ECEvaluation();
				UserInfoDao userInfoDao = new UserInfoImpl();
				UavatarDao uavatarDao = new UavatarImpl();
				int darenid = rs1.getInt("daren");
				String darenname = userInfoDao.FindUnameByUid(darenid);
				String darenavatar = uavatarDao.FindUavatarByUid(darenid);
				String darencomment = rs1.getString("dcomment");
				EcEvaluation.setUname(darenname);
				EcEvaluation.setUavatar(darenavatar);
				EcEvaluation.setComment(darencomment);
				EcEvaluation.setDc("customn");//达人角色
				evaluationlist.add(EcEvaluation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationlist;
	}

	//不区分角色
	@Override
	public ArrayList<ECEvaluation> ShowTaskEvaluation(int tid) {
		Connection con=null;
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		try {
			con=DBUtils.getConnection();
			Statement stmt = con.createStatement();
			String sql = "select * from evaluation where tid = "+tid;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				ECEvaluation EcEvaluation = new ECEvaluation();
				UserInfoDao userInfoDao = new UserInfoImpl();
				UavatarDao uavatarDao = new UavatarImpl();
				int customnid = rs.getInt("customn");
				String customnname = userInfoDao.FindUnameByUid(customnid);
				String customnavatar = uavatarDao.FindUavatarByUid(customnid);
				String customncomment = rs.getString("ucomment");
				EcEvaluation.setUavatar(customnavatar);
				EcEvaluation.setUname(customnname);
				EcEvaluation.setComment(customncomment);
				evaluationlist.add(EcEvaluation);
				
				int darenid = rs.getInt("daren");
				String darenname = userInfoDao.FindUnameByUid(darenid);
				String darenavatar = uavatarDao.FindUavatarByUid(darenid);
				String darencomment = rs.getString("dcomment");
				EcEvaluation.setUavatar(darenname);
				EcEvaluation.setUname(darenavatar);
				EcEvaluation.setComment(darencomment);
				evaluationlist.add(EcEvaluation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationlist;
	}

	//不区分达人和用户，展示评论
	@Override
	public ArrayList<ECEvaluation> ShowMyEvaluation(int uid) {
		Connection con=null;
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		try {
			con=DBUtils.getConnection();
			Statement stmt = con.createStatement();
			String sql = "select * from evaluation where daren = '"+uid+"' or customn = "+uid+"";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ECEvaluation EcEvaluation = new ECEvaluation();
				UserInfoDao userInfoDao = new UserInfoImpl();
				UavatarDao uavatarDao = new UavatarImpl();
				if(rs.getInt("customn") == uid) {
					int darenid = rs.getInt("daren");
					String darenname = userInfoDao.FindUnameByUid(darenid);
					String darenavatar = uavatarDao.FindUavatarByUid(darenid);
					String darencomment = rs.getString("dcomment");
					EcEvaluation.setUavatar(darenname);
					EcEvaluation.setUname(darenavatar);
					EcEvaluation.setComment(darencomment);
					EcEvaluation.setEstars(rs.getFloat("stars"));
					evaluationlist.add(EcEvaluation);
				}else if(rs.getInt("daren") == uid) {
					int customnid = rs.getInt("customn");
					String customnname = userInfoDao.FindUnameByUid(customnid);
					String customnavatar = uavatarDao.FindUavatarByUid(customnid);
					String customncomment = rs.getString("ucomment");
					EcEvaluation.setUavatar(customnavatar);
					EcEvaluation.setUname(customnname);
					EcEvaluation.setComment(customncomment);
					EcEvaluation.setEstars(rs.getFloat("stars"));
					evaluationlist.add(EcEvaluation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationlist;
	}
	
	
	
}
