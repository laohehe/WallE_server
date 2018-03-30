package indi.api.servlet;

import indi.api.dao.*;
import indi.api.model.Collection;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OneCollect
 */
@WebServlet("/OneCollect")
public class OneCollectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String method;
    private String openid;
    private short type;
    private int item_id;
    private Date date;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OneCollectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//判断是收藏 还是取消收藏
		method = request.getParameter("method");
		//获取相关参数
		openid = request.getParameter("openid");
		type = (short)Integer.parseInt(request.getParameter("category"));
		item_id = Integer.parseInt(request.getParameter("item_id"));
		date = Date.valueOf(request.getParameter("date"));
		Collection collection = new Collection(openid, type, item_id, date);
		//收藏
		if(method.endsWith("add")){
			AddCollect myAddCollect = new AddCollect();
			myAddCollect.addCollect(collection);
		}else if(method.endsWith("cancel")){//取消收藏
			CancelCollect myCancelCollect = new CancelCollect();
			myCancelCollect.cancelCollect(collection);
		}else{
			
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
