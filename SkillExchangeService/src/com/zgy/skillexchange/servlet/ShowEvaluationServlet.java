package com.zgy.skillexchange.servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.gson.JsonArray;
import com.zgy.skillexchange.bean.ECEvaluation;
import com.zgy.skillexchange.dao.EvaluationDao;
import com.zgy.skillexchange.impl.EvaluationImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShowEvaluationServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8"); 
		int method = Integer.parseInt(req.getParameter("method"));
		switch (method) {
		case 1:
			this.method1(req, resp);
			break;
		case 2:
			this.method2(req, resp);
			break;
		default:
			break;
		}

	}
	
	public void method1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8"); 
		int uid = Integer.parseInt(req.getParameter("uid"));
		EvaluationDao evaluationDao = new EvaluationImpl();
		JSONArray jsonArray = null;
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		try {
			evaluationlist = evaluationDao.ShowEvaluation(uid);
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
	
	public void method2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8"); 
		int tid = Integer.parseInt(req.getParameter("tid"));
		EvaluationDao evaluationDao = new EvaluationImpl();
		JSONArray jsonArray = null;
		ArrayList<ECEvaluation> evaluationlist = new ArrayList<ECEvaluation>();
		try {
			evaluationlist = evaluationDao.ShowTaskEvaluation(tid);
			jsonArray = JSONArray.fromObject(evaluationlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JSONObject json = new JSONObject();
			json.put("evaluationlist2",jsonArray);
			//以json的形式传到客户端
			resp.getWriter().write(json.toString());
		}
	}
	
}
