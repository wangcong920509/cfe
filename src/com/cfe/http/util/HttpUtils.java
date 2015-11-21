package com.cfe.http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpUtils {
	
	private HttpUtils() {
		
	}

	public static String sendHttpClientPost(String path, Map<String, String> map, String encode) 
			throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(map!=null && !map.isEmpty()){
			for(Map.Entry<String, String> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
		}
			//实现将请求的参数封装到表单，请求体中
			UrlEncodedFormEntity entiry = new UrlEncodedFormEntity(list,encode);
			//使用Post方式提交数据
			HttpPost httpPost = new HttpPost(path);
			httpPost.setEntity(entiry);
			HttpParams params = httpPost.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			//指定post请求
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				return changeInputStream(httpResponse.getEntity().getContent(),encode);
			}
		
		return "";
	}
	
    public static String doGet(String url, String encode) throws ClientProtocolException, IOException {  
        HttpGet httpGet = new HttpGet(url);  
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
        httpGet.addHeader("Connection", "Keep-Alive");  
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");  
        httpGet.addHeader("Cookie", ""); 
        
        //指定post请求
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(httpGet);
		if(httpResponse.getStatusLine().getStatusCode()==200) {
			return changeInputStream(httpResponse.getEntity().getContent(),encode);
		}
        
        return "";  
  
    } 
	
	public static String changeInputStream(InputStream inputStream,String encode) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
				System.out.println(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
