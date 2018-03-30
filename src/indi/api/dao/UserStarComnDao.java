package indi.api.dao;

import java.sql.ResultSet;

import indi.api.util.DbUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserStarComnDao {
private static Connection conn;
	
	public UserStarComnDao(){
		super();
	}
	
	public boolean getIfStar(String openid,int comnId){
		try{
			ResultSet rs = null;
			String sql = "select count(*) from userstarcomn where openid = ? and comnid = ?";
			conn = DbUtil.getConnection();
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, openid);
			ptmt.setInt(2, comnId);
			rs = ptmt.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) > 0) {
					if(conn !=null){
						conn.close();
					}
					if(ptmt!=null){
						ptmt.close();
					}
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void starComn(String openid,int comnId){
		try{
			String sql = "insert into userstarcomn(openid,comnid) values(?,?)";
			conn = DbUtil.getConnection();
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, openid);
			ptmt.setInt(2, comnId);
			ptmt.execute();
			if(ptmt!=null){
				ptmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
