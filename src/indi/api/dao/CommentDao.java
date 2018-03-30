package indi.api.dao;



import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import indi.api.model.Comments;
import indi.api.util.DbUtil;
import indi.api.vo.CommentInfo;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class CommentDao {
	private static Connection conn;
	
	public CommentDao(){
		super();
	}
	//该评论的点赞数加一
	public void addComnNum(int comnId){
		try{
			String  sql = "update comments set starnum = starnum + 1 where id = ?";
			conn = DbUtil.getConnection();
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setInt(1, comnId);
			if(ptmt.execute()){
				if(ptmt != null){
					ptmt.close();
				}
				if(conn != null){
					conn.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//获取该文章的所有评论信息
	public Comments[] getComments(Short type,int item_id,java.sql.Date date){
		int commentsNum = 0;
		ResultSet rs = null;
		try{
			String sql = "select * from comments where type = ? and item_id = ? and date = ?";
			conn = DbUtil.getConnection();
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setShort(1, type);
			ptmt.setInt(2, item_id);
			ptmt.setDate(3, date);
			rs = ptmt.executeQuery();
			//获取结果集的长度
			rs.last();
			commentsNum = rs.getRow();
			rs.beforeFirst();
			if(commentsNum != 0 ){
				Comments[] comments = new Comments[commentsNum];
				int index = 0;//定义数组的下标
				while(rs.next()){
					comments[index] = new Comments(rs.getInt(1),rs.getString(2), rs.getShort(3), rs.getInt(4), rs.getDate(5), rs.getString(6), rs.getInt(7),rs.getTimestamp(8));
					index++;
				}
				return comments;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void addComment(Comments comments){
		try{
			//HH是24小时制
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			conn = DbUtil.getConnection();
			String sql = "insert into comments(openid,type,item_id,date,comment,comntime)"
					+ "values(?,?,?,?,?,?)";
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setString(1, comments.getOpenid());
			ptmt.setShort(2, comments.getType());
			ptmt.setInt(3, comments.getItem_id());
			ptmt.setDate(4, comments.getDate());
			ptmt.setString(5, comments.getComment());
			ptmt.setString(6, fmt.format(new Date().getTime()));
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
	public int GetCommentsNum(short type,int item_id,java.sql.Date date){
		int commentsNum = 0;
	//public int GetCommentsNum(Comments comments){
		try{
			ResultSet rs = null;
			String sql ="select count(*) from comments where type = ? and item_id = ? and date = ?";
			conn = DbUtil.getConnection();
			PreparedStatement ptmt = (PreparedStatement)conn.prepareStatement(sql);
			ptmt.setShort(1, type);
			ptmt.setInt(2, item_id);
			ptmt.setDate(3, date);
			rs = ptmt.executeQuery();
			while(rs.next()){
				commentsNum = rs.getInt(1);
				//System.out.println(commentsNum);
			}
			if(ptmt != null){
				ptmt.close();
			}
			if(conn != null){
				conn.close();
			}
			return commentsNum;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}	
	}
}
