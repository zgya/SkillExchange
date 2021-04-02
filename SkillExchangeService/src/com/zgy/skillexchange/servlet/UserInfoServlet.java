package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.impl.UserInfoImpl;

import net.sf.json.JSONObject;

public class UserInfoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int method = Integer.parseInt(req.getParameter("method"));
		switch (method) {
		case 1:
			this.ShowUname(req, resp);
			break;
		case 2:
			this.UpdateUname(req, resp);
			break;
		}
	}
	
	//method=1 登陆时显示用户名字
	public void ShowUname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uname = null;
		resp.setContentType("text/html; charset=UTF-8");
		String uphone = req.getParameter("uphone");
		try {
			UserInfoDao userInfoDao = new UserInfoImpl();
			uname = userInfoDao.FindUnameByUphone(uphone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			//数据以json的形式传出去
			json.put("uname",uname);
			resp.getWriter().write(json.toString());
		}
	}
	
	//method=2 修改用户名字
	public void UpdateUname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uphone = req.getParameter("uphone");
		String uname = req.getParameter("uname");
		try {
			UserInfoDao userInfoDao = new UserInfoImpl();
			userInfoDao.UpdateUname(uphone, uname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			//数据以json的形式传出去
			json.put("result",true);
			resp.getWriter().write(json.toString());
		}
		
	}
}
