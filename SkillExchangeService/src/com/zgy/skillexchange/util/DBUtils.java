package com.zgy.skillexchange.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
	 public static final  String URL="jdbc:mysql://localhost:3306/skillexchange";     
     public static final String NAME = "root";
     public static final String PASSWORD = null;
     public static final String DREIVER = "com.mysql.jdbc.Driver"; 
     
     static {
        try {
            //加载驱动器 
            Class.forName(DREIVER);                 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return  DriverManager.getConnection(URL, NAME, PASSWORD);//创建与数据库的链接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//设置一个公共的关闭链接、释放资源的方法    .   因为每次只要进行了增,删,查,改 之后 都必须要 事件,  那么就设置一个公共的方法
    //而关闭资源要从 ResultSet先关闭-->,再到 PreparedStatement-->,最后到 Connection关闭
    public static void Close(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
