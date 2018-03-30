package indi.api.dao;

import indi.api.model.Collection;
import indi.api.util.DbUtil;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GetIfCollected {
	private static Connection conn;
	private boolean isCollected = false;

	public boolean getIfollected(Collection collection) {
		try {
			ResultSet rs = null;
			conn = DbUtil.getConnection();
			String sql = "select count(*) from collections where openid = ? and type = ? and item_id = ? and date = ?";
			if (conn != null) {
				PreparedStatement ptmt = (PreparedStatement) conn
						.prepareStatement(sql);
				ptmt.setString(1, collection.getOpenid());
				ptmt.setShort(2, collection.getType());
				ptmt.setInt(3, collection.getItem_id());
				ptmt.setDate(4, collection.getDate());
				// 执行sql语句
				rs = ptmt.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) > 0) {
						isCollected = true;
					}
				}
				// 关闭连接
				if (rs != null)
					rs.close();
				if (ptmt != null)
					ptmt.close();
				if (conn != null)
					conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCollected;
	}
}
