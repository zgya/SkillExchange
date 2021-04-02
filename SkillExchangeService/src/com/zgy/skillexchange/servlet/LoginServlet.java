package com.zgy.skillexchange.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  
import net.sf.json.JSONObject; 
import com.zgy.skillexchange.util.*;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  String uphone = req.getParameter("uphone"); //用于接收android前台的输入的值，此处参数必须要与你前台的值相对应
          String upassword= req.getParameter("upassword");  
          boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致  
          resp.setContentType("text/html; charset=UTF-8");  
          PrintWriter out = resp.getWriter();  
          Connection con=null;
          JSONObject json = new JSONObject();
         //   JsonConfig jsonConfig = new JsonConfig();
         // jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonDateValueProcessor());
          try  
          {  
              con=DBUtils.getConnection(); 
              Statement stmt=con.createStatement();  
              String sql="select * from userinfo where uphone ="+uphone+" and upassword="+upassword; 
              ResultSet rs=stmt.executeQuery(sql);  
              while(rs.next())  
              {  
                  type=true;       
              }
              
          }  
          catch(Exception ex)  
          {  
              ex.printStackTrace();  
          }  
          finally  
          {  
        	  DBUtils.Close(null, null, con);
              json.put("login_result",  type );
              resp.getWriter().write(json.toString());
              out.flush();  
              out.close();  
          }  
  }
}
