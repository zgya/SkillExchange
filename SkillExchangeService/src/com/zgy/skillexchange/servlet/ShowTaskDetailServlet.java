package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zgy.skillexchange.bean.Task;
import com.zgy.skillexchange.dao.TaskDao;
import com.zgy.skillexchange.impl.TaskImpl;

import net.sf.json.JSONObject;

public class ShowTaskDetailServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		int tid = (Integer.parseInt(req.getParameter("tid")));
		Task task = new Task();
		String jsonTaskdetail = null;
		try {
			TaskDao taskDao = new TaskImpl();
			task = taskDao.FindTaskByTid(tid);
			Gson gson = new Gson();
			jsonTaskdetail = gson.toJson(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("taskdetail", jsonTaskdetail);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
	}

}
