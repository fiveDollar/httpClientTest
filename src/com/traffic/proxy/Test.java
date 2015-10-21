package com.traffic.proxy;


public class Test {
	public static void main(String[] args) {
		String  data_log = "{'fm':'alhm','ensrcid':'h5_mobile','order':'4','mu':'http://www.belle8.com/forum.php?mod=viewthread&tid=544093&ordertype=2&page=1'}";
		System.out.println();
		for(String data:data_log.replace("{", "").replace("}", "").replace("'", "").split(",")){
			System.out.println(data);
		}
	}
}
