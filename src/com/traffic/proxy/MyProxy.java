package com.traffic.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

public class MyProxy {
	ArrayList<String> proxy ;
	static String dog  = "dog2";
	public int port ;
	public String host;
	public  void change(){
		proxy = getproxy();
		while (proxy == null||proxy.size()<1) {
			proxy = getproxy();
		}
		int len = proxy.size();
		int random = (int) (Math.random() * len);
		String[] a = null;
		while (a==null||a.length != 2) {
			a = proxy.get(random).split(":");
		}
		host = a[0];
		port = Integer.parseInt(a[1]);
		
	}
	public  ArrayList<String> getproxy() {
		ArrayList<String> data = new ArrayList<String>();
		File f = new File("proxy.txt");
		if(!f.exists()){
			try {
				setproxy();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		InputStreamReader ir;
		try {
			ir = new InputStreamReader(new FileInputStream(f), "utf-8");
			BufferedReader br = new BufferedReader(ir);
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.matches("\\d*.\\d*.\\d*.\\d*:\\d*")){
					data.add(line);
				}
			}
			ir.close();
			br.close();
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		proxy = data;
		return data;
	}
	public static void setproxy() throws IOException{
		GetWebcon g =new GetWebcon();
		String webcon =null;
		while(webcon==null||webcon.length()<100){
			System.out.println("get proxy");
			//http://www.szwindoor.com/test/proxy.php?sign=YXD76THGFodksYU765dIUH
			//http://108.171.250.42/wdproxy.php?sign=YXD76THGFodksYU765dIUHASDFGH12345&num=1000
			webcon=	g.getwebcon("http://www.szwindoor.com/test/proxy.php?sign=YXD76THGFodksYU765dIUH").replaceAll("<br/>", "\r\n");
		}
		File f = new File("proxy.txt");
		if(!f.exists()){
			f.createNewFile();
		}
		OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		BufferedWriter bw = new BufferedWriter(ow);
		bw.write(webcon);
		bw.flush();
		bw.close();
		ow.close();
	}
	public void changeWithcheck(){
		change();
		while(checkProxy(host, port)!=1){
			change();
		}
	}
	private int checkProxy(String host,int port) {
		
		String url = "http://www.szwindoor.com/test/test_proxy.php";
		HttpURLConnection con = null;
		BufferedReader in = null;
		try {
			con = (HttpURLConnection)new URL(url).openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port)));
			con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
//			long c = System.currentTimeMillis();
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
//			long b = System.currentTimeMillis()-c;
//			if(b>1000){
//				return 6;
//			}
			StringBuilder sb = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			String numStr = sb.toString();
			return Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			return 5;
		} catch (IOException e) {
			return 4;
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public boolean check(){
		if(port!=0&&host!=null){
			return true;
		}else{
			return false;
		}
	}
	public String toString(){
		if(port!=0&&host!=null){
			return host +":"+ port+"";
		}else{
			return null;
		}
	}
}
