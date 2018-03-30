package indi.api.dao;

import indi.api.model.Collection;
import indi.api.util.DbUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class AddCollect {
	private static Connection conn;

	public AddCollect() {
		super();
	}

	public void addCollect(Collection collection) {
		try {
			String sql = "INSERT INTO collections(openid,type,item_id,date)"
					+ "values(?,?,?,?)";
			conn = DbUtil.getConnection();
			// 预编译，减少sql执行时间
			PreparedStatement ptmt = (PreparedStatement) conn
					.prepareStatement(sql);
			ptmt.setString(1, collection.getOpenid());
			ptmt.setShort(2, collection.getType());
			ptmt.setInt(3, collection.getItem_id());
			ptmt.setDate(4, collection.getDate());
			// 执行
			ptmt.execute();
			// 此处代码需要优化，因为可能在执行关闭之前就已经抛出了异常。
			// 关闭连接
			if (ptmt != null)
				ptmt.close();
			if (conn != null)
				conn.close();
			System.out.println("用户" + collection.getOpenid() + "已点赞");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
