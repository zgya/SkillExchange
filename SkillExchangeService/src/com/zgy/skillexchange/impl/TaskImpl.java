package com.zgy.skillexchange.impl;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.DispatchTaskDao;
import com.zgy.skillexchange.dao.TaskDao;
import com.zgy.skillexchange.dao.UavatarDao;
import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.util.DBUtils;

public class TaskImpl implements TaskDao{
	public int[] FindTidByUid(int uid) {
		int tid[] = new int[20];
		int i = 0;
		Connection con=null;
		
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			Statement stmt1 = con.createStatement();
			String sql = "select tid from dispatchtask where uid ="+uid;
			String sqlString = "select tid from taskstate where tstate = 1 or tstate = 2";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSet rSet = stmt1.executeQuery(sqlString);
			while(rs.next()) {
				tid[i] = rs.getInt("tid");
				i++;
			}
			while(rSet.next()){
				tid[i] = rSet.getInt("tid");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tid;
	}
	
	//展示任务列表（用户自己发布的、已中标的和任务已过期的除外，只显示正在招标中的任务)
	//0-招标中，1-已中标，2-已过期
	public ArrayList<Task> ShowTask(int tid[]) {
		int tdone;
		ArrayList<Task> taskList = new ArrayList<Task>(); 
		//这条语句只是创建了n个对象的引用。
		//需要为每一个对象引用创建新的对象
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i = 0, j = 0;
		Connection con=null;
		String sql = "select * from task where tid != ";
		String sqlString;
		StringBuilder sb = new StringBuilder(sql);
		//动态生成sql
		for(j = 0; j < tid.length; j++) {
			if(j == 0) {
				sb.append(tid[j]+"");
			}else {
				sb.append(" and tid != ");
				sb.append(tid[j]+"");
			}
		}
		sql = sb.toString();
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				sqlString = "select tstate from taskstate where tid =";
				Task task = new Task();
				int rstid = rs.getInt("tid");
				task.setTid(rstid);
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append(sqlString);
				sBuilder.append(rstid+"");
				ResultSet rs1 = stmt1.executeQuery(sBuilder.toString());
				while(rs1.next()) {
					task.setTstate(rs1.getInt("tstate"));
				}
				//根据tid寻找uid
				DispatchTaskDao dispatchTaskDao = new DispatchTaskImpl();
				int rsuid = dispatchTaskDao.FindUidByTid(rstid);
				task.setUid(rsuid);
				//发布任务的人的头像
				UavatarDao uavatarDao = new UavatarImpl();
				String rsuavatar = uavatarDao.FindUavatarByUid(rsuid);
				//发布任务的人的名字
				UserInfoDao userInfoDao = new UserInfoImpl();
				String rsuname = userInfoDao.FindUnameByUid(rsuid);
				task.setUname(rsuname);
				task.setUavatar(rsuavatar);
				task.setTtitle(rs.getString("ttitle"));
				task.setTcontent(rs.getString("tcontent"));
				task.setTprice(rs.getFloat("tprice"));
				task.setTdeadlineDate(df.format(rs.getTimestamp("tdeadline")));
				task.setTbids(rs.getInt("tbids"));
				task.setTaddress(rs.getString("taddress"));
				taskList.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}

	@Override
	public Task FindTaskByTid(int tid) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Task task = new Task();
		Connection con=null;
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			String sql = "select * from task where tid ="+tid;
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				//根据tid寻找uid
				DispatchTaskDao dispatchTaskDao = new DispatchTaskImpl();
				int rsuid = dispatchTaskDao.FindUidByTid(tid);
				//发布任务的人的头像
				UavatarDao uavatarDao = new UavatarImpl();
				String rsuavatar = uavatarDao.FindUavatarByUid(rsuid);
				task.setUavatar(rsuavatar);
				//发布任务的人的名字
				UserInfoDao userInfoDao = new UserInfoImpl();
				String rsuname = userInfoDao.FindUnameByUid(rsuid);
				task.setUname(rsuname);
				task.setTdeadlineDate(df.format(rs.getTimestamp("tdeadline")));
				task.setTprice(rs.getFloat("tprice"));
				task.setTtitle(rs.getString("ttitle"));
				task.setTcontent(rs.getString("tcontent"));
				task.setTaddress(rs.getString("taddress"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return task;
	}

	//增加一条任务
	@Override
	public void insertTask(Task task) {
		Connection con=null;
		String sql = "insert into task(ttitle,tcontent,tprice,taddress,tdeadline,tbids) values ('"+task.getTtitle()+"','"+task.getTcontent()+"','"+task.getTprice()+"','"+task.getTaddress()+"','"+task.getTdeadlineDate()+"','"+task.getTbids()+"')";
		String sqlString2 = "select * from task";
		try {
			con=DBUtils.getConnection();
			Statement stmt=con.createStatement();  
			Statement statement = con.createStatement();
			Statement statement2 = con.createStatement();
			stmt.execute(sql);
			ResultSet rSet = statement2.executeQuery(sqlString2);
			rSet.last();
			int tid = rSet.getInt("tid");
			String sqlString = "insert into dispatchtask(uid,tid) values ('"+task.getUid()+"','"+tid+"')";
			statement.execute(sqlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
