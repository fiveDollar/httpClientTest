package com.traffic.mobile_send;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import HttpClientUtil.EntityReader;
import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;

public class QueryTwo {
//	word=test
//			tn=iphone
//			rn=10
//			wpo=base
	public String scheme = "https";
	public String host = "www.baidu.com";
	public String path = "/from=844b/s";
	public String word;
	public String rsv_iqid;

	public String ts;
	public String t_kt = new Random().nextInt(100)+100+"";
	public String rsv_sug4;
	public String inputT;
	public String sa = "ib";
	public String ss ="100";
	public String ms="1";
	public CookieStore cookieStore = new BasicCookieStore();
	public String[] headers = {
		"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
		,"Connection:keep-alive"
		,"version:HTTP/1.1"
		,"referer:https://www.baidu.com/"
		,"Accept-Encoding:gzip, deflate, sdch"
		,"Accept-Language:zh-CN,zh;q=0.8"
		,"X-Requested-With:XMLHttpRequest"
		,"User-Agent:Mozilla/5.0 (Linux; Android 4.4.4; C8817D Build/HuaweiC8817D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36"
	};
	
	public QueryTwo(MyRespone Rec1Respone,MyRespone homeRespone,String word){
		this.word = word;
		ts = System.currentTimeMillis()+"";
		ts = ts.substring(ts.length()-7);
		Document doc = Jsoup.parse(EntityReader.ReadeEntity(homeRespone.getBhe(), "utf8"));
		rsv_iqid = doc.select("[name=rsv_iqid]").attr("name");
		ms = doc.select("[name=ms]").attr("name");
		long now = System.currentTimeMillis();
		String sugNow = (now-new Random().nextInt(6000)-1000)+"";
		rsv_sug4 = sugNow.substring(sugNow.length()-6);
		String inputTStr = now+"";
		inputT = inputTStr.substring(inputTStr.length()-6);
		getCookieFromHome(homeRespone);
//		getCookieFromTr1(Rec1Respone);
		
	}
	
	private void getCookieFromHome(MyRespone myRespone){
		Header[] headers = myRespone.getHttpClientContext().getResponse().getHeaders("Set-Cookie");
		for (Header header : headers) {
//			if(!header.getValue().contains("__bsi")){
				BasicClientCookie cookie = new BasicClientCookie(header.getValue().split(";")[0].split("=")[0], header.getValue().split(";")[0].split("=")[1]);
				cookie.setDomain(host);
				cookie.setPath("/");
				cookieStore.addCookie(cookie);
//			}
		}
	}
	
	private void getCookieFromTr1(MyRespone myRespone){
		Header[] headers = myRespone.getHttpClientContext().getResponse().getHeaders("Set-Cookie");
		for (Header header : headers) {
				BasicClientCookie cookie = new BasicClientCookie(header.getValue().split(";")[0].split("=")[0], header.getValue().split(";")[0].split("=")[1]);
				cookie.setDomain(host);
				cookie.setPath("/");
				cookieStore.addCookie(cookie);
				System.out.println(header.getValue().split(";")[0].split("=")[1]);
		}
		BasicClientCookie cookie = new BasicClientCookie("PLUS", "1");
		cookie.setDomain(host);
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
	}
	public URI getURI(){
		String[] parameter = {"word="+word,"ts="+ts,"t_kt="+t_kt,"rsv_iqid="+rsv_iqid
				,"sa="+sa,"ms="+ms,"ms="+ms,"rsv_sug4="+rsv_sug4,"inputT"+inputT
				,"ss="+ss};
		URI uri =null;
		try {
			uri = UriBuilder.getURI(scheme, host, path, parameter);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}
}
