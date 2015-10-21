package com.traffic.mobile_send;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import HttpClientUtil.EntityReader;
import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;

public class PreWord {
	public String scheme = "https";
	public String host = "m.baidu.com";
	public String path = "/su";
	public String pre = "1";
	public String p = "3";
	public String ie = "utf-8";
	public String from = "wise_web";
	public String callback ="jsonp4";
	public String wd;
	public String _;
	public String sugsid;
	public String net;
	public String os;
	public String sp;
	public CookieStore cookieStore = new BasicCookieStore();
	public String[] headers = {
		":host:m.baidu.com",
		"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
		,"Connection:keep-alive"
		,"accept:*/*"
		,"version:HTTP/1.1"
		,"referer:https://www.baidu.com/"
		,":scheme:https"
		,":method:GET"
		,"Accept-Encoding:gzip, deflate, sdch"
		,"Accept-Language:zh-CN,zh;q=0.8"
//		,"Upgrade-Insecure-Requests:1"
		,"User-Agent:Mozilla/5.0 (Linux; Android 4.4.4; C8817D Build/HuaweiC8817D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36"
	};
	
	public PreWord(MyRespone Rec1Respone,MyRespone homeRespone,String wd){
		Document doc = Jsoup.parse(EntityReader.ReadeEntity(homeRespone.getBhe(), "utf8"));
		this.wd = wd;
		_=System.currentTimeMillis()+"";
		Element input = doc.select("#commonBase").get(0);
		sugsid = input.attr("ata-sugsid");
		net = input.attr("data-tanet");
		os = input.attr("data-osid");
		sp = input.attr("data-spd");
//		System.out.println();
		getCookieFromHome(homeRespone);
//		getCookieFromTr1(Rec1Respone);
		
	}
	
	private void getCookieFromHome(MyRespone myRespone){
		Header[] headers = myRespone.getHttpClientContext().getResponse().getHeaders("Set-Cookie");
		for (Header header : headers) {
			if(!header.getValue().contains("__bsi")){
				BasicClientCookie cookie = new BasicClientCookie(header.getValue().split(";")[0].split("=")[0], header.getValue().split(";")[0].split("=")[1]);
				cookie.setDomain(host);
				cookie.setPath("/");
//				System.out.println(cookie);
				cookieStore.addCookie(cookie);
			}
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
		String[] parameter = {"pre="+pre,"ie="+ie,"from="+from,"sugsid="+sugsid,"net="+net,
				"os="+os,"sp="+sp,"callback="+callback,"wd="+wd,"_="+_};
		URI uri =null;
		try {
			uri = UriBuilder.getURI(scheme, host, path, parameter);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}
	
}
