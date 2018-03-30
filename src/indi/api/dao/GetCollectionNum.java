package indi.api.dao;

import indi.api.util.DbUtil;

import java.sql.Date;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GetCollectionNum {
	private static Connection conn;
	private short type;
	private int item_id;
	private Date date;

	public GetCollectionNum(short type, int item_id, Date date) {
		super();
		this.type = type;
		this.item_id = item_id;
		this.date = date;
	}

	public int getCollectionNum() {
		int collectionNum = 0;
		try {
			conn = DbUtil.getConnection();
			String sql = "select count(*) from collections where type = ? and item_id = ? and date = ?";
			// 预编译，减少sql执行时间
			if (conn != null) {
				ResultSet rs = null;
				PreparedStatement ptmt = (PreparedStatement) conn
						.prepareStatement(sql);
				ptmt.setShort(1, type);
				ptmt.setInt(2, item_id);
				ptmt.setDate(3, date);
				// 执行
				rs = ptmt.executeQuery();
				while (rs.next()) {
					collectionNum = rs.getInt(1);
					// System.out.println("collectionNum:"+collectionNum);
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
		return collectionNum;
	}
}
