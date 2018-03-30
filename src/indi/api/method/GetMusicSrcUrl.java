package indi.api.method;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetMusicSrcUrl {
	private static int times = 0;//记录js请求的次数
	private static String listen_file = "";
	public static String getMusicSrcUrl(String url){
		times = 0;
		//System.out.println(url);
		//url:http://h.xiami.com/one-share.html?id=1775695528
		//HttpUnit 请求web页面
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		//设置webClient的相关参数
		wc.getOptions().setUseInsecureSSL(true);
		wc.getOptions().setJavaScriptEnabled(true);
		wc.getOptions().setCssEnabled(false);
		//支持AJax
		wc.setAjaxController(new NicelyResynchronizingAjaxController());  
		wc.getOptions().setThrowExceptionOnScriptError(false);
		wc.getOptions().setTimeout(10000);
		wc.getOptions().setDoNotTrackEnabled(false);
		wc.setWebConnection(new WebConnectionWrapper(wc){
			public WebResponse getResponse(WebRequest request) throws IOException{
				WebResponse response = super.getResponse(request);
				if(response.getContentType().equals("application/json")){
					times++;
				}
				if(times == 2){
					JsonParser jsonParser = new JsonParser();
					//System.out.println("lalala"+response.getContentAsString());
					StringBuilder sb = new StringBuilder();
					sb.append(response.getContentAsString());
					//json:songdetail15121960779011(...)
					//sb.substring(sb.indexOf("(")+1, sb.lastIndexOf(")"));
					//解析json并获取到listen_file
					JsonObject json = (JsonObject) jsonParser.parse(sb.substring(sb.indexOf("(")+1, sb.lastIndexOf(")")).toString());
					JsonObject data = (JsonObject) json.get("data").getAsJsonObject();
					JsonObject song = (JsonObject) data.get("song").getAsJsonObject();
					listen_file = song.get("listen_file").getAsString();
					//System.out.println(listen_file);
				}
				return response;
			}
		});
		try {
			HtmlPage page = wc.getPage(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		wc.waitForBackgroundJavaScript(1000*3);
		wc.close();
		
		return listen_file;
	}
}
