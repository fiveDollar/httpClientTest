package com.traffic.proxy;

import java.io.IOException;

public class ProxyService {
	public static void start(){
		try {
			MyProxy.setproxy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RunMyproxy rm = new RunMyproxy();
		Thread s = new Thread(rm);
		s.start();
	}
}
