package indi.api.servlet;

import indi.api.dao.CommentDao;
import indi.api.dao.UserDao;
import indi.api.dao.UserStarComnDao;
import indi.api.model.Comments;
import indi.api.model.User;
import indi.api.vo.CommentInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class OneComment
 */
@WebServlet("/OneComment")
public class OneComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserStarComnDao userStarComnDao = new UserStarComnDao();
	private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String methodType = null;  
    private String openid = null;
    private short type;
    private int item_id;
    private Date date;
    private String comment;
    private int comnId;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OneComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置response响应格式
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		methodType = request.getParameter("methodType");	
		CommentDao commentDao = new CommentDao();
		if(methodType.equals("add")){
			openid = request.getParameter("openid");
			type = (short)Integer.parseInt(request.getParameter("category"));
			item_id = Integer.parseInt(request.getParameter("item_id"));
			date = Date.valueOf(request.getParameter("date"));
			comment = request.getParameter("comment");
			Comments comments = new Comments(openid, type, item_id, date, comment);
		    commentDao.addComment(comments);
		}else if(methodType.equals("getComnByDate")){
			type = (short)Integer.parseInt(request.getParameter("category"));
			item_id = Integer.parseInt(request.getParameter("item_id"));
			date = Date.valueOf(request.getParameter("date"));
			Comments[] commentList = commentDao.getComments(type, item_id, date);
			//fmt.format(commentList[i].getComntime());
			//发送数据给客户端
			sendCommentsToClient(commentList,response);
		}else if(methodType.equals("starComn")){
			openid = request.getParameter("openid");
			comnId = Integer.parseInt(request.getParameter("comnId"));
			userStarComnDao.starComn(openid, comnId);
			commentDao.addComnNum(comnId);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	
	private void sendCommentsToClient(Comments[] comments,HttpServletResponse response){
		try{
			boolean isUserStarComn = false;//默认未点赞
			//如果当前文章存在评论
			if(comments != null){
			Gson gson = new Gson();
			UserDao userDao = new UserDao();
			User usertmp = null;
			String openidtmp = null;
			int commentsCount = comments.length;
			CommentInfo[] commentInfoList = new CommentInfo[commentsCount];
			for(int i = 0;i<commentsCount;i++){
				isUserStarComn = userStarComnDao.getIfStar(openid, comments[i].getId());
				openidtmp = comments[i].getOpenid();
				usertmp = userDao.getUserByOpenid(openidtmp);
				commentInfoList[i] = new CommentInfo(openidtmp, usertmp.getNickName(), 
						usertmp.getAvataUrl(), comments[i].getComment(), 
						comments[i].getStarnum(),fmt.format(comments[i].getComntime()),
						comments[i].getId(),isUserStarComn);
			}
			String jsonComnStr = gson.toJson(commentInfoList);
			PrintWriter pw = response.getWriter();
			pw.write(jsonComnStr);
			pw.flush();
			pw.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
