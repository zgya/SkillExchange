package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.bean.JieBa;
import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.JieBaDao;
import com.zgy.skillexchange.dao.TaskDao;
import com.zgy.skillexchange.impl.JieBaImpl;
import com.zgy.skillexchange.impl.TaskImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TaskJieBaServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		int uid = Integer.parseInt(req.getParameter("uid"));
		TaskDao taskDao = new TaskImpl();
		JieBaDao jieBaDao = new JieBaImpl();
		JSONArray jsonArray = null;
		ArrayList<JieBa> jieBas = new ArrayList<JieBa>();
		ArrayList<Task> taskList = new ArrayList<Task>(); 
		try {
			int []tid = taskDao.FindTidByUid(uid);
			taskList = taskDao.ShowTask(tid);
			jieBas = jieBaDao.JieBaFunc(taskList);
			jsonArray = JSONArray.fromObject(jieBas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("jieBas", jsonArray);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
	}

}
