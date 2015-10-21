package HttpClientUtil;

import org.apache.http.client.methods.HttpGet;

public class MyHttpGetBuilder {
	public static HttpGet addHeader(String[] headers,HttpGet httpGet){
		for (int i = 0; i < headers.length; i++) {
			httpGet.addHeader(headers[i].split(":")[0], headers[i].split(":")[1]);
		}
		
		
		return httpGet;
	}
}
