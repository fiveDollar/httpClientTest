package httpClientTest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class TestOne {
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URIBuilder()
				.setScheme("http")
				.setHost("www.baidu.com")
				.setPath("/s")
				.setParameter("ie", "utf-8")
				.setParameter("f", "8")
				.setParameter("rsv_bp", "0")
				.setParameter("rsv_idx", "1")
				.setParameter("tn", "baidu")
				.setParameter("wd", "cf")
				.setParameter("rsv_pq", "b3765584000f7e5a")
				.setParameter("rsv_t", "a3d5tgEgvDvUFvucrAvExRYSbxvghIvQmJriolphg%2Bw7w%2FVfZhs8NgvrZuA")
				.setParameter("rsv_enter", "1")
				.setParameter("rsv_sug3", "4")
				.setParameter("rsv_n", "2")
				.build();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse httpRes = null;
		InputStream in = null;
		ByteArrayOutputStream bos = null;
		//BufferedHttpEntity 可以缓存entiy 以便于多次读取
		BufferedHttpEntity bhe = null;
		//可以用来处理response 
//		ResponseHandler
		HttpContext httpContext = new BasicHttpContext();
		HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
		try {
			
//			clientContext.
//			httpGet.abort();
//			URIUtils.resolve(baseURI, reference)
			httpRes = httpClient.execute(httpGet,clientContext);
			HttpHost target = clientContext.getTargetHost();
			HttpRequest request = clientContext.getRequest();
			HttpResponse response = clientContext.getResponse();
			RequestConfig config = clientContext.getRequestConfig();
//			System.out.println(target);
//			System.out.println(request);
//			System.out.println(response);
//			System.out.println(config);
//			System.out.println(target);
//			System.out.println("protocolVersion "+httpRes.getProtocolVersion());
//			System.out.println("Locale "+httpRes.getLocale());
			Header[] header = httpRes.getAllHeaders();
			for (Header header2 : header) {
				System.out.println(header2);
			}
//			System.out.println("status "+httpRes.getStatusLine().toString());
			HttpEntity httpEntity = httpRes.getEntity();

			if (httpClient!=null) {
				bhe = new BufferedHttpEntity(httpEntity);
			}
			
//			System.out.println(httpEntity.isStreaming());
//			System.out.println(httpEntity.getContentEncoding());
//			System.out.println(httpEntity.getContentType());
//			System.out.println(httpEntity.);
//			in = httpEntity.getContent();
//			bos = new ByteArrayOutputStream();
//			byte[] temp = new byte[1024 * 1024];
//			int size = in.read(temp);
//			while(size>0){
//				bos.write(temp, 0, size);
//				try {
//					size = in.read(temp);
//				} catch (Exception e) {
//					e.printStackTrace();
//					size = 0;
//				}
//			}
//			String webcon = bos.toString("utf-8");
//			System.out.println(webcon);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bos!=null)
					bos.close();
				if(in!=null)
					in.close();
				if(httpRes!=null)
					httpRes.close();
				if(httpClient!=null)
					httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				System.out.println(bhe.getContent());
//				InputStream tin = bhe.getContent();
//				InputStreamReader tinr = new InputStreamReader(tin, "utf-8");
//				BufferedReader br = new BufferedReader(tinr);
//				String line = "";
//				while((line=br.readLine())!=null){
//					System.out.println(line);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			System.out.println(bhe.isRepeatable());
		}
		
//		System.out.println(clientContext.getRequest());
	}
}
