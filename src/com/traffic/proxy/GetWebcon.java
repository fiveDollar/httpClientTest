package com.traffic.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;


public class GetWebcon {
	int timeout = 1000*3;
	public String getwebcon(String url,MyProxy proxy) {
		HttpURLConnection	connection=null;
		ByteArrayOutputStream	bos=null;
		InputStream in = null;
		try {
			
			if(proxy!=null&&proxy.check()) {
				connection = (HttpURLConnection)new URL(url).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.host, proxy.port)));
			} else {
				connection = (HttpURLConnection)new URL(url).openConnection();
			}
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 4 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
			connection.setReadTimeout(timeout);
			connection.setConnectTimeout(timeout);
			in = connection.getInputStream();
			byte[] temp = new byte[1024 * 1024];
			bos = new ByteArrayOutputStream();
			int size = in.read(temp);
			while (size > 0) {
				bos.write(temp, 0, size);
				try {
					size = in.read(temp);
				} catch (Exception e) {
					size = 0;
				}
			}
			String webcon = bos.toString("utf-8");
			return webcon;
		} catch (MalformedURLException e1) {
			return "chaoshi";
		} catch (IOException e) {
			return "chaoshi";
		} finally {
			try {
				if(in!=null){
					in.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.disconnect();
		}
	
	}
	public String getwebcon(String url) {
		HttpURLConnection	connection=null;
		ByteArrayOutputStream	bos=null;
		InputStream in = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setReadTimeout(timeout);
			connection.setConnectTimeout(timeout);
			in = connection.getInputStream();
			byte[] temp = new byte[1024 * 1024];
			bos = new ByteArrayOutputStream();
			int size = in.read(temp);
			while (size > 0) {
				bos.write(temp, 0, size);
				try {
					size = in.read(temp);
				} catch (Exception e) {
					size = 0;
				}
			}
			String webcon = bos.toString("utf-8");
			
			return webcon;
		} catch (MalformedURLException e1) {
			return "chaoshi";
		} catch (IOException e) {
		//	e.printStackTrace();
			return "chaoshi";
		} finally {
			try {
				if(in!=null){
					in.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			connection.disconnect();
		}
	
	}
	
	

}
