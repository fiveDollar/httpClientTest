package HttpClientUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import com.traffic.comm_class.MyRespone;

public class GetSender {
	public static MyRespone sendGet(final HttpGet httpGet,final CloseableHttpClient httpClient){
		final HttpClientContext httpContext = HttpClientContext.adapt(new BasicHttpContext());
		final MyRespone myR =  new MyRespone(httpContext, null);
		try{
			
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000)
					.setConnectTimeout(3000)
					.build();
			
			httpGet.setConfig(requestConfig);
			
			
			
			Thread thread_start = new Thread(new Runnable() {
				public void run() {// 用一个独立的线程启动WebDriver
				try {
					final CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
					final BufferedHttpEntity bhe = new BufferedHttpEntity(httpResponse.getEntity());
//					bhe.
					myR.setBhe(bhe);
					myR.setHttpClientContext(httpContext);
				} catch (Exception e) {
//					e.printStackTrace();
				}
				
				}
			});
			thread_start.start();
			int count = 0;
			while (true) {
				count++;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count >=5 && thread_start.isAlive()) {
					thread_start.interrupt();
					System.out.println("timeOut");
					throw new RuntimeException("线程获取网页超时");
					
				} else if(!thread_start.isAlive()){
					break;
				}else if(count <= 60 && thread_start.isAlive()){
					
				}
			}
			
			
		}catch(Exception e){
//			e.printStackTrace();
		}finally{
			try {
				if(httpClient!=null)
					httpClient.close();
		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return myR;
	}
}
