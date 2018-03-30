package indi.api.servlet;

import indi.api.dao.*;
import indi.api.model.Collection;
import indi.api.model.Comments;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class GetInfoOfArticle
 */
@WebServlet("/GetInfoOfArticle")
public class GetInfoOfArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String openid;
    private short type;
    private int item_id;
    private Date date;   
    private int collectionNum;
    private int commentNum;
    private boolean isCollected;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInfoOfArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		openid = request.getParameter("openid");
		type = (short)Integer.parseInt(request.getParameter("category"));
		item_id = Integer.parseInt(request.getParameter("item_id"));
		date = Date.valueOf(request.getParameter("date"));
		//实例化用户收藏实体类
		Collection collections = new Collection(openid,type,item_id,date);
		//实例化评论操作类
		CommentDao commentDao = new CommentDao();
		GetCollectionNum getCollectionNum = new GetCollectionNum(type,item_id,date);
		//获取该文章收藏数
		collectionNum = getCollectionNum.getCollectionNum();
		//System.out.println("收藏数"+collectionNum);
		//判断是否该用户是否已收藏
		GetIfCollected getIfCollected = new GetIfCollected();
		isCollected = getIfCollected.getIfollected(collections);
		//System.out.println("是否已点赞"+isCollected);
		//获取文章评论数
		commentNum = commentDao.GetCommentsNum(type, item_id, date);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = null;
		JsonObject 	jsonObject = new JsonObject();
		try{
			pw = response.getWriter();
			jsonObject.addProperty("collectionNum", collectionNum);
			jsonObject.addProperty("isCollected", isCollected);
			jsonObject.addProperty("commentNum", commentNum);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pw.write(jsonObject.toString());
			pw.flush();
			pw.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
