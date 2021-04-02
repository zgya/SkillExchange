package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.TaskDao;
import com.zgy.skillexchange.impl.TaskImpl;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShowTaskServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	//向客户端传递除用户自己发布的任务外的所有其他人发布的任务
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		int uid = Integer.parseInt(req.getParameter("uid"));
		TaskDao taskDao = new TaskImpl();
		JSONArray jsonArray = null;
		ArrayList<Task> taskList = new ArrayList<Task>(); 
		try {
			int []tid = taskDao.FindTidByUid(uid);
			taskList = taskDao.ShowTask(tid);
			jsonArray = JSONArray.fromObject(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("tasks", jsonArray);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
	}
}
