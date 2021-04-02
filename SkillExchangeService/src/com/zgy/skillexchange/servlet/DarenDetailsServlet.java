package com.zgy.skillexchange.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zgy.skillexchange.bean.DarenDetail;
import com.zgy.skillexchange.dao.DarenDao;
import com.zgy.skillexchange.impl.DarenImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DarenDetailsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); 
		int uid = Integer.parseInt(req.getParameter("uid"));
		DarenDetail darenDetail = new DarenDetail();
		String jsonDarendetail = null;
		try {
			DarenDao darenDao = new DarenImpl();
			darenDetail = darenDao.ShowDarenDetails(uid);
			Gson gson = new Gson();
			jsonDarendetail = gson.toJson(darenDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("darendetail", jsonDarendetail);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
		
	}
	

}
