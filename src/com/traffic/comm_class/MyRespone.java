package com.traffic.comm_class;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.BufferedHttpEntity;


public class MyRespone {
	private HttpClientContext httpClientContext;
	private BufferedHttpEntity bhe;
	public int flag=0;
	public MyRespone(HttpClientContext httpClientContext,BufferedHttpEntity bhe){
		this.httpClientContext = httpClientContext;
		this.bhe = bhe;
	}
	
	public HttpClientContext getHttpClientContext() {
		return httpClientContext;
	}
	public void setHttpClientContext(HttpClientContext httpClientContext) {
		this.httpClientContext = httpClientContext;
	}
	public BufferedHttpEntity getBhe() {
		return bhe;
	}
	public void setBhe(BufferedHttpEntity bhe) {
		this.bhe = bhe;
	}
	
}
