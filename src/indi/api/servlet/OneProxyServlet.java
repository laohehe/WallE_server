package indi.api.servlet;

import indi.api.method.GetMusicSrcUrl;
import indi.api.param.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;





import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class ParamGet
 */
@WebServlet("/ParamGet")
public class OneProxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String url;
    private static Boolean isMusic ;
    
    private void process(HttpServletRequest req,HttpServletResponse resp){
    	resp.setContentType("text/json");
    	resp.setCharacterEncoding("UTF-8");
    	isMusic = false;
    	url = urltoOneUrl(req);
    	//取得连接
    	try{
    		HttpURLConnection huc = (HttpURLConnection)new URL(url).openConnection();
        	huc.connect();
        	//获取响应流
        	BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
        	String line = null;
        	StringBuilder sb = new StringBuilder();
        	while((line=br.readLine())!=null){
        		sb.append(line);
        	}
        	PrintWriter out = resp.getWriter();
        	//获取音乐src
        	if(isMusic){
        		String listen_file = "";
        		JsonParser parse = new JsonParser();
        		try{
        			JsonObject json = (JsonObject) parse.parse(sb.toString());
        			JsonObject data = json.get("data").getAsJsonObject();
        			String web_url = data.get("web_url").getAsString();
        			//System.out.println(web_url);
        			listen_file = GetMusicSrcUrl.getMusicSrcUrl(web_url);
        			json.addProperty("listen_file", listen_file);
        			//sb.insert(1, "listen_file:\""+listen_file+"\",");
        			//json.addProperty("listen_file", listen_file);
        			//System.out.println(sb.toString());
        			out.print(json.toString());
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}else{
        		out.print(sb);
        	}
        	out.close();
        	huc.disconnect();
    	}catch(Exception e){
    		e.printStackTrace();
   		}
    }
    /*
    private String[] parse(Map map) throws UnsupportedEncodingException{
    	String[] arr = { "", "" };  
        Iterator iter = map.entrySet().iterator();  
        while (iter.hasNext()) {  
            Map.Entry me = (Map.Entry) iter.next();  
            String[] varr = (String[]) me.getValue();  
            if ("servletName".equals(me.getKey())) {  
                // 取出servlet名称  
                arr[0] = varr[0];  
            } else {  
                // 重新组装参数字符串  
                for (int i = 0; i < varr.length; i++) {  
                    // 参数需要进行转码，实现字符集的统一  
                    arr[1] += "&" + me.getKey() + "="  
                            + URLEncoder.encode(varr[i], "utf-8");  
                }  
            }  
        }  
        arr[1] = arr[1].replaceAll("^&", "");  
        return arr;
    }
    */
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OneProxyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			process(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
    }
	
	//url处理函数
	private static String urltoOneUrl(HttpServletRequest request){
		String url = null;
		String idlist;
		String item_id;
		String date;
		String resApiType = request.getParameter("type");
		//System.out.println(resApiType);
		for(int i=0;i<Parameter.getApiType().length;i++){
			if(resApiType.equalsIgnoreCase(Parameter.getApiType()[i])){
				//System.out.println(i);
				switch(i){
				//获取插画详细  已在前台代码处理
				//case 0:
				//	break;
				//获取阅读详细 essay
				case 1:{
					item_id = request.getParameter("item_id");
					url = "http://v3.wufazhuce.com:8000/api/essay/"+item_id+"/?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
					}
				//获取连载详细serialcontent
				case 2:
					item_id = request.getParameter("item_id");
					url = "http://v3.wufazhuce.com:8000/api/serialcontent/"+item_id+"/?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
				//获取问答详细question
				case 3:
					item_id = request.getParameter("item_id");
					url = "http://v3.wufazhuce.com:8000/api/question/"+item_id+"/?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
				//获取音乐详细music
				case 4:
					item_id = request.getParameter("item_id");
					url = "http://v3.wufazhuce.com:8000/api/music/detail/"+item_id+"/?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					isMusic = true;
					break;
				//获取影视详细movie
				case 5:
					item_id = request.getParameter("item_id");
					url = "http://v3.wufazhuce.com:8000/api/movie/"+item_id+"/story/1/0?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
				//获取最新的idlist
				case 6:{
					idlist = request.getParameter("idlist");
					url = "http://v3.wufazhuce.com:8000/api/onelist/"+idlist+"/0?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
					}
				//获取onelist 
				case 7:{
					url = "http://v3.wufazhuce.com:8000/api/onelist/idlist/?channel=wdj&version=4.3.4&uuid=00000000-26a8-efea-a7d7-62612b9cafaa&platform=web";
					break;
					}
				//根据具体的日期获取内容
				case 9:{
					date = request.getParameter("date");
					//System.out.println(date);
					url = "http://v3.wufazhuce.com:8000/api/channel/one/"+date+"/channel=yingyongbao&version=4.5.1&platform=android";
					break;
					}
				}
			}
		}
	return url;
    }
}
