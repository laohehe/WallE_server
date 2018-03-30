package indi.api.servlet;

import indi.api.dao.UserDao;
import indi.api.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OneGetUserInfo
 */
@WebServlet("/OneGetUserInfo")
public class OneGetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String openid;
    private String nickName;
    private String avatarUrl;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OneGetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			UserDao userDao = new UserDao();
			String openid = request.getParameter("openid");
			String nickName = request.getParameter("nickName");
			String avatarUrl = request.getParameter("avatarUrl");	
			User user = new User(openid,nickName,avatarUrl);
			if(userDao.ifExistByOpenid(openid) == true){
				//表中存在记录，先删除
				userDao.deleteUserByOpenid(openid);
			}
			//用户表新增记录
			userDao.addUser(user);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
