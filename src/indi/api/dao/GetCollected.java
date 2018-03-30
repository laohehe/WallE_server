package indi.api.dao;

import indi.api.model.Collection;
import indi.api.util.DbUtil;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GetCollected {
	private static Connection conn;
	public GetCollected() {
		super();
	}

	public Collection[] getCollectedByOpenid(String openid) {
		String sql = "SELECT * FROM collections where openid = ?";
		int collectedCount = 0;//当前用户收藏文章总数
		try {
			conn = DbUtil.getConnection();
			//结果集可滚动
			PreparedStatement ptmt = (PreparedStatement) conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ptmt.setString(1, openid);
			ResultSet rs = ptmt.executeQuery();
			collectedCount = rs.getMetaData().getColumnCount();
			//获取结果集的长度
			rs.last();
			collectedCount = rs.getRow();
			rs.beforeFirst();
            if(collectedCount != 0){
            	Collection[] collection = new Collection[collectedCount];
            	int index = 0;//定义数组的下标
    			while (rs.next()){
    				collection[index] = new Collection(rs.getString(2),rs.getShort(3),rs.getInt(4),rs.getDate(5));
    				index++;
    			} 
    			return collection;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
