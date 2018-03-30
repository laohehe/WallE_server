package indi.api.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DbUtil {
	public static final String URL = "jdbc:mysql://localhost:3306/web_api?characterEncoding=utf8&useSSL=true";
	public static final String USER = "root";
	public static final String PASSWORD = "1064521309";
	public static Connection getConnection(){
		Connection conn = null;
		try{
			//1.加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			//2.获得数据库连接
			conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	
}
