package com.traffic.mobile_send;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;

public class Tgif {
	public static String scheme = "https";
	public static String host = "www.baidu.com";
	public static String path = "/static/tj.gif";
//	public static String[] parameter =null;
	//当前时间戳
	public long time = 0;
	public String[] headers = {
		"Accept:image/webp,image/*,*/*;q=0.8"
		,"Connection:keep-alive"
		,"Accept-Encoding:gzip, deflate, sdch"
		,"Host:www.baidu.com"
		,"Accept-Language:zh-CN,zh;q=0.8"
		,"Upgrade-Insecure-Requests:1"
		,"User-Agent:Mozilla/5.0 (Linux; Android 4.4.4; C8817D Build/HuaweiC8817D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36"
		,"Referer:https://www.baidu.com/"
		};
	public BasicCookieStore cookieStore = new BasicCookieStore();
	
	public Tgif(MyRespone HomeRespone,MyRespone lastRespone){
		setCookie(HomeRespone,(lastRespone==null?null:lastRespone.getHttpClientContext().getResponse().getLastHeader("Set-Cookie").getValue().split(";")[0]));
	}
	
	private void setCookie(MyRespone myRespone,String __bsi){
		Header[] headers = myRespone.getHttpClientContext().getResponse().getHeaders("Set-Cookie");
		
		for (Header header : headers) {
			if(__bsi!=null&&header.getValue().split(";")[0].contains("__bsi"))
				continue;
			
			BasicClientCookie cookie = new BasicClientCookie(header.getValue().split(";")[0].split("=")[0], header.getValue().split(";")[0].split("=")[1]);
			cookie.setDomain(host);
			cookie.setPath("/");
			cookieStore.addCookie(cookie);
		}
		
		if(__bsi==null)
			return;
		
		BasicClientCookie cookie = new BasicClientCookie("PLUS", "1");
		cookie.setDomain(host);
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		
	}
	public URI getURI(){
		time = System.currentTimeMillis();
		String[] parameter = {"time="+time};
		try {
			return UriBuilder.getURI(scheme, host, path, parameter);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
