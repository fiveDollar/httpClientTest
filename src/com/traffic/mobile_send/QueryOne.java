package com.traffic.mobile_send;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import HttpClientUtil.EntityReader;
import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;

public class QueryOne {
//	word=test
//			tn=iphone
//			rn=10
//			wpo=base
	public String scheme = "https";
	public String host = "www.baidu.com";
	public String path = "/s";
	public String word = "test";
	public String tn = "iphone";
	public String rn = "10";
	public String wpo = "base";
	public String ssid ="0_0_";
	public String ts = "lbs";
	public String from = "844b";
	public String ms="1";
	public String mod ="1";
	public String isid;
	public String sa = "ib";
	public String at="3";
	public String ss ="100";
	public String _;
	public CookieStore cookieStore = new BasicCookieStore();
	public String[] headers = {
		"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
		,"Connection:keep-alive"
		,"accept:image/webp,image/*,*/*;q=0.8"
		,"version:HTTP/1.1"
		,"referer:https://www.baidu.com/"
		,"scheme:https"
		,"method:GET"
		,"Accept-Encoding:gzip, deflate, sdch"
		,"Accept-Language:zh-CN,zh;q=0.8"
		,"Upgrade-Insecure-Requests:1"
		,"User-Agent:Mozilla/5.0 (Linux; Android 4.4.4; C8817D Build/HuaweiC8817D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36"
	};
	
	public QueryOne(MyRespone Rec1Respone,MyRespone homeRespone,String word){
		this.word = word;
		ts = System.currentTimeMillis()+"";
		ts = ts.substring(ts.length()-7);
		Document doc = Jsoup.parse(EntityReader.ReadeEntity(homeRespone.getBhe(), "utf8"));
		ssid = doc.select("#commonBase").attr("data-prepath").replace("#", "&");
		isid = doc.select("#commonBase").attr("data-logid");
		_=System.currentTimeMillis()+"";
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
				System.out.println(host);
		}
		BasicClientCookie cookie = new BasicClientCookie("PLUS", "1");
		cookie.setDomain(host);
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
	}

	public URI getURI(){
		String[] parameter = {"word="+word,"tn="+tn,"rn="+rn,"wpo="+wpo,ssid.split("&")[0],ssid.split("&")[1],ssid.split("&")[2],ssid.split("&")[3]
				,"ts="+ts,"from="+from,"ms="+ms,"mod="+mod,"isid="+isid
				,"sa="+sa,"at="+at,"ss="+ss,"_="+_};
		URI uri =null;
		try {
			uri = UriBuilder.getURI(scheme, host, path, parameter);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}
}
