package com.traffic.mobile_send;

import java.util.Random;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import HttpClientUtil.GetSender;
import HttpClientUtil.MyHttpClientBuilder;

import com.traffic.comm_class.MyRespone;
import com.traffic.proxy.MyProxy;

public class TgifSend implements Runnable{
	MyRespone homePage;
//	public static int flag =0;
	public MyProxy mp;
	public TgifSend(MyRespone homePage,MyProxy mp){
		this.homePage = homePage;
		this.mp = mp;
	}
	@Override
	public void run() {
		try {
			Tgif tg = new Tgif(homePage, null);
			CloseableHttpClient httpClient =  MyHttpClientBuilder.build(mp.host, mp.port,tg.cookieStore);
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(tg.getURI());
			MyRespone next = GetSender.sendGet(httpGet, httpClient);
			for(int i=0;i<=30+new Random().nextInt(10);i++){
				while(System.currentTimeMillis()-tg.time<1000){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				tg = new Tgif(homePage, next);
				
				CloseableHttpClient httpClientNext =  MyHttpClientBuilder.build("222.92.117.87", 31287,tg.cookieStore);
				HttpGet httpGetNext = new HttpGet(tg.getURI());
				System.out.println(tg.time);
				next = GetSender.sendGet(httpGetNext, httpClientNext);
//				System.out.println(flag);
				if(homePage.flag==1){
					break;
				}
			}
		} catch (Exception e) {
			
		}
		homePage.flag=1;
	}
	
}
