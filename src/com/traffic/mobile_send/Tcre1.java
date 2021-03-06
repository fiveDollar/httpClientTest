package com.traffic.mobile_send;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import HttpClientUtil.EntityReader;
import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;

public class Tcre1 {
	public String scheme = "https";
	public String host = "m.baidu.com";
	public String path = "/tc";
	public String tcreq4log = "1";
	public String ct = "10";
	public String cst = "1";
	public String ref = "index_iphone";
	public String w ="0_0_";
	public String clkFrom = "lbs";
	public String clk_info = "mainpage";
	public String cardIndex = "2";
	//时间戳
	public String r;
	//从html中id=commonBase 元素属性 data-prepath的值
	public String ssid;
	//HomePage的traceid的后19位
	public String qid;
	public String lid;
	public String plus_lvs;
	public CookieStore cookieStore;
	public String[] headers = {
		":host:m.baidu.com",
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
	public Tcre1(MyRespone myRespone){
		String traceId =myRespone.getHttpClientContext().getResponse().getFirstHeader("traceid").getValue(); 
		qid = traceId.substring(traceId.length()-19);
		lid = qid;
		Document doc = Jsoup.parse(EntityReader.ReadeEntity(myRespone.getBhe(), "utf8"));
		ssid = doc.select("#commonBase").attr("data-prepath").replace("#", "&");
		plus_lvs = doc.select("#index-card").attr("data-lsversion");
		r = System.currentTimeMillis()+"";
		cookieStore = getCookie(myRespone);
		
	}
	private CookieStore getCookie(MyRespone myRespone){
		Header[] headers = myRespone.getHttpClientContext().getResponse().getHeaders("Set-Cookie");
		CookieStore cookieStore = new BasicCookieStore();
		for (Header header : headers) {
//			if(!header.getValue().contains("__bsi")){
				BasicClientCookie cookie = new BasicClientCookie(header.getValue().split(";")[0].split("=")[0], header.getValue().split(";")[0].split("=")[1]);
				cookie.setDomain(host);
				cookie.setPath("/");
				cookieStore.addCookie(cookie);
				System.out.println(header.getValue().split(";")[0].split("=")[1]);
//			}
		}
		BasicClientCookie cookie = new BasicClientCookie("PLUS", "1");
		cookie.setDomain(host);
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		return cookieStore;
	}
	public URI getURI(){
		String[] parameter = {"tcreq4log="+tcreq4log,ssid.split("&")[0],ssid.split("&")[1],ssid.split("&")[2],"qid="+qid,"ct="+ct,"cst="+cst,"ref="+ref,"lid="+lid,
				"w="+w,"clk_from="+clkFrom,"clk_info="+clk_info,"card_index="+cardIndex,"r="+r};
		URI uri =null;
		try {
			uri = UriBuilder.getURI(scheme, host, path, parameter);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}
}
