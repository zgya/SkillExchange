package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.dao.DarenDao;
import com.zgy.skillexchange.impl.DarenImpl;

public class AddskillServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); 
		int uid = Integer.parseInt(req.getParameter("uid"));
		String uskills = req.getParameter("uskills");
		try {
			DarenDao dao = new DarenImpl();
			dao.InsertDarenSkills(uid, uskills);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
