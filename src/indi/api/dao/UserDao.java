package indi.api.dao;

import java.sql.ResultSet;

import indi.api.model.User;
import indi.api.util.DbUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserDao {
	private static Connection conn;
	
	public UserDao(){
		super();
	}
	public User getUserByOpenid(String openid){
		try{
			User user = null;
			String sql = "select * from users where openid = ?";
			conn = DbUtil.getConnection();
			ResultSet rs = null;
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, openid);
			rs = ptmt.executeQuery();
			if(rs.next()){
				user = new  User(rs.getString(4), rs.getString(2), rs.getString(3));
			}
			if(ptmt != null){
				ptmt.close();
			}
			if(conn != null){
				conn.close();
			}
			return user;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public boolean ifExistByOpenid(String openid){
		try{
			String sql = "select count(*) from users where openid = ?";
		    conn = DbUtil.getConnection();
		    ResultSet rs = null;
			//预编译，减少sql执行时 
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, openid);
			//执行
			rs = ptmt.executeQuery();
			if(ptmt != null){
				ptmt.close();
			}
			if(conn != null){
				conn.close();
			}
			if(rs != null){
			    return true;
			}else{
			    return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteUserByOpenid(String openid){
		try{
			conn = DbUtil.getConnection();
			String sql = "delete from users where openid = ?";
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, openid);
			ptmt.execute();
			if(ptmt != null){
				ptmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addUser(User user){
		try{
			conn = DbUtil.getConnection();
			String sql = "insert into users(nickName,avatarUrl,openid)"
					+ "values(?,?,?)";
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, user.getNickName());
			ptmt.setString(2, user.getAvataUrl());
			ptmt.setString(3, user.getOpenid());
			ptmt.execute();
			if(ptmt != null){
				ptmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
