package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.util.DBUtils;

import net.sf.json.JSONObject;

public class UidServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uphone = req.getParameter("uphone");
		int uid = 0;
		resp.setContentType("text/html; charset=UTF-8");  
		Connection con=null;
        JSONObject json_uid = new JSONObject();
        try {
        	 con=DBUtils.getConnection(); 
             Statement stmt=con.createStatement();  
             String select_uid = "select uid from userinfo where uphone ="+uphone;
             ResultSet rs_uid = stmt.executeQuery(select_uid);
             while(rs_uid.next()) {
           	  uid = rs_uid.getInt("uid");
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally {
        	json_uid.put("uid", uid);
        	resp.getWriter().write(json_uid.toString());
        }
	}

}
