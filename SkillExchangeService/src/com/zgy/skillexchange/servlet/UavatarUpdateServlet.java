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

import net.sf.json.JSONObject;

public class UavatarUpdateServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); 
		String uavatar = req.getParameter("uavatar");
		String uphone = req.getParameter("uphone");
		JSONObject json = new JSONObject();
        try {
        	UavatarDao uavatarDao = new UavatarImpl();
            uavatarDao.UpdateUavatar(uphone, uavatar);//将用户头像存入数据库
            json.put("update_uavatar", true );
            resp.getWriter().write(json.toString()); 
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        
	}
	
}
