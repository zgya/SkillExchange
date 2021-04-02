package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.dao.UavatarDao;
import com.zgy.skillexchange.impl.UavatarImpl;
import com.zgy.skillexchange.util.DBUtils;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class UavatarServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	//查看
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uavatar = "uavatar";
		String uphone = req.getParameter("uphone");
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter(); 
		try {
			UavatarDao uavatarDao = new UavatarImpl();
			uavatar = uavatarDao.FindUavatarByUphone(uphone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			//数据以json的形式传出去
			json.put("uavatar",uavatar);
			resp.getWriter().write(json.toString());
	        out.flush();  
	        out.close(); 
		}
		
	}
	
}
