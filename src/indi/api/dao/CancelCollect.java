package indi.api.dao;

import indi.api.model.Collection;
import indi.api.util.DbUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class CancelCollect {
	private static Connection conn;

	public CancelCollect() {
		super();
	}

	public void cancelCollect(Collection user) {
		try {
			String sql = "DELETE FROM collections where openid = ? and type = ? and item_id = ? and date = ?";
			conn = DbUtil.getConnection();
			// 预编译，减少sql的执行时间
			PreparedStatement ptmt = (PreparedStatement) conn
					.prepareStatement(sql);
			ptmt.setString(1, user.getOpenid());
			ptmt.setShort(2, user.getType());
			ptmt.setInt(3, user.getItem_id());
			ptmt.setDate(4, user.getDate());
			// 执行
			ptmt.execute();
			// 关闭连接
			if (ptmt != null)
				ptmt.close();
			if (conn != null)
				conn.close();
			System.out.println("用户" + user.getOpenid() + "已取消点赞");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
