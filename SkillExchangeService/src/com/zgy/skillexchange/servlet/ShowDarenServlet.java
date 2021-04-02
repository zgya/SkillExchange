package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.bean.Daren;
import com.zgy.skillexchange.dao.DarenDao;
import com.zgy.skillexchange.impl.DarenImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShowDarenServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		ArrayList<Daren> DarenList = new ArrayList<Daren>();
		int uid = Integer.parseInt(req.getParameter("uid"));
		JSONArray jsonArray = null;
		try {
			DarenDao darenDao = new DarenImpl();
			DarenList = darenDao.ShowDaren(uid);
			jsonArray = JSONArray.fromObject(DarenList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("darenlist",jsonArray);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
		
	}
	

}
