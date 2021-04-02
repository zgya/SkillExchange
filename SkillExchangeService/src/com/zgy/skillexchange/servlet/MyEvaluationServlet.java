package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgy.skillexchange.bean.ECEvaluation;
import com.zgy.skillexchange.dao.EvaluationDao;
import com.zgy.skillexchange.impl.EvaluationImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyEvaluationServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");  
		int uid = Integer.parseInt(req.getParameter("uid"));
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		JSONArray jsonArray = null;
		try {
			EvaluationDao evaluationDao = new EvaluationImpl();
			evaluationlist = evaluationDao.ShowMyEvaluation(uid);
			jsonArray = JSONArray.fromObject(evaluationlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("evaluationlist",jsonArray);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
	}

}
