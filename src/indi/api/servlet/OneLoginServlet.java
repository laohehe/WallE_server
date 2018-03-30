package indi.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OneLoginServlet extends HttpServlet {
	private static String code;//用户登录凭证
	private static String AppId = "wx12c05fe6161a1035";//小程序唯一标识 
	private static String AppSecret = "d5ec224172e4a05d4d63e37d0f14d9d5";

	/**
	 * Constructor of the object.
	 */
	public OneLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	//code 换取 session_key
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		code = request.getParameter("code");//用户登录凭证
		//System.out.println("code:"+code);
		//微信官方api
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+AppId+"&secret="+AppSecret+"&js_code="+code+"&grant_type=authorization_code";
		try{
			HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
			huc.connect();
			//获取响应流
        	BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
        	String line = null;
        	StringBuilder sb = new StringBuilder();
        	while((line=br.readLine())!=null){
        		sb.append(line);
        	}
        	//关闭连接
        	huc.disconnect();
        	//System.out.println("code 换取 session_key的json包:");
        	//System.out.println(sb);
        	//解析json数据
        	JsonParser jsonParser = new JsonParser();
        	JsonObject jsonObject = (JsonObject) jsonParser.parse(sb.toString());
        	//String session_key = jsonObject.get("session_key").getAsString();
        	String openid = jsonObject.get("openid").getAsString();
        	
        	//将openid返回给客户端
        	PrintWriter out = response.getWriter();
        	out.print(openid);
        	out.close();
		}catch(Exception e ){
			e.printStackTrace();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
