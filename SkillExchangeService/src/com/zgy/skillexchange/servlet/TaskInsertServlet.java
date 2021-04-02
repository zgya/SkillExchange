package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.TaskDao;
import com.zgy.skillexchange.impl.TaskImpl;

import net.sf.json.JSONObject;

public class TaskInsertServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String insertTask = req.getParameter("insertTask");
		TaskDao taskDao = new TaskImpl();
		Task task = new Task();
		System.out.print(insertTask);
		JSONObject jsonObject=JSONObject.fromObject(insertTask);
		task.setTaddress(jsonObject.getString("taddress"));
		task.setTbids(jsonObject.getInt("tbids"));
		task.setTcontent(jsonObject.getString("tcontent"));
		task.setTdeadlineDate(jsonObject.getString("tdeadlineDate"));
		task.setTprice(Float.parseFloat(jsonObject.getString("tprice")));
		task.setTtitle(jsonObject.getString("ttitle"));
		task.setUid(Integer.parseInt(jsonObject.getString("uid")));
		try {
			taskDao.insertTask(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
