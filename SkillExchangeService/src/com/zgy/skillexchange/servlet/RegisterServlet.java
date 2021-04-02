package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.bean.UserInfo;
import com.zgy.skillexchange.dao.UserInfoDao;
import com.zgy.skillexchange.impl.UserInfoImpl;

import net.sf.json.JSONObject;

public class RegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");  
		String registerString = req.getParameter("registerString");
		System.out.println(registerString);
		UserInfo userInfo = new UserInfo();
		JSONObject jsonObject=JSONObject.fromObject(registerString);
		userInfo.setUphone(jsonObject.getString("uphone"));
		userInfo.setUpassword(jsonObject.getString("upasswrod"));
		try {
			UserInfoDao userInfoDao = new UserInfoImpl();
			userInfoDao.Insertuserinfo(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
