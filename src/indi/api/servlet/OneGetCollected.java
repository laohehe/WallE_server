package indi.api.servlet;

import indi.api.dao.GetCollected;
import indi.api.model.Collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class OneGetCollected
 */
@WebServlet("/OneGetCollected")
public class OneGetCollected extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String url = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OneGetCollected() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JsonObject jsonObject = null;
		JsonArray jsonArray = null;
		//保存用户收藏的文章
		JsonArray collectedArray = new JsonArray();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		//定义http连接
		HttpURLConnection huc = null;
		//定义缓冲流
		BufferedReader br = null;
		StringBuilder sb = null;
		String openid = request.getParameter("openid");
		GetCollected myGetCollected = new GetCollected();
		//获取结果集
		Collection[] users = myGetCollected.getCollectedByOpenid(openid);
		//如果结果集不为空
		if(users!=null){
		    for(int i = 0;i<users.length;i++){
		    	//根据日期获取到数据
		        url = "http://v3.wufazhuce.com:8000/api/channel/one/"+users[i].getDate()
		        		+"/channel=yingyongbao&version=4.5.1&platform=android";
		        try{
		        	huc = (HttpURLConnection)new URL(url).openConnection();
		        	huc.connect();
		            //获取响应流	
		        	br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		        	String line = null;
		        	sb = new StringBuilder();
		        	while((line = br.readLine())!= null){
		        		sb.append(line);
		        	}
		        	jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
		        	//System.out.println(jsonObject.get("data").getAsJsonObject().get("content_list").getAsJsonArray().get(0).getAsJsonObject().get("forward").getAsString());
		        	jsonArray = jsonObject.get("data").getAsJsonObject().get("content_list").getAsJsonArray();
		        	for(int j = 0;j<jsonArray.size();j++){
		        		if(jsonArray.get(j).getAsJsonObject().get("item_id").getAsInt() == users[i].getItem_id()
		        				&&jsonArray.get(j).getAsJsonObject().get("category").getAsInt() == users[i].getType()){
		        			collectedArray.add(jsonArray.get(j).getAsJsonObject());
		        		}
		        	}
		        	
		        	huc.disconnect();
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		    }
		    PrintWriter pw = response.getWriter();
		    pw.print(collectedArray);
		    pw.flush();
		    pw.close();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
