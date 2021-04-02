package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.bean.JieBa;
import com.zgy.skillexchange.bean.KuJieBa;
import com.zgy.skillexchange.dao.JieBaDao;
import com.zgy.skillexchange.impl.JieBaImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KuJieBaServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		JieBaDao jieBaDao = new JieBaImpl();
		JSONArray jsonArray = null;
		ArrayList<KuJieBa> kuJieBas = new ArrayList<KuJieBa>();
		kuJieBas = jieBaDao.KuJieBaFunc();
		jsonArray = JSONArray.fromObject(kuJieBas);
		JSONObject json = new JSONObject();
		json.put("kuJieBas", jsonArray);
		//以json的形式传到客户端
		resp.getWriter().write(json.toString());
	}

}
