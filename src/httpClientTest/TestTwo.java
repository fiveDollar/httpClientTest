package httpClientTest;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import HttpClientUtil.GetSender;
import HttpClientUtil.MyHttpClientBuilder;
import HttpClientUtil.MyHttpGetBuilder;
import HttpClientUtil.UriBuilder;

import com.traffic.comm_class.MyRespone;
import com.traffic.mobile_send.HomePage;
import com.traffic.mobile_send.PreWord;
import com.traffic.mobile_send.QueryOne;
import com.traffic.mobile_send.QueryTwo;
import com.traffic.mobile_send.Tcre1;
import com.traffic.mobile_send.Tcre2;
import com.traffic.mobile_send.Tcre3;
import com.traffic.mobile_send.TgifSend;
import com.traffic.proxy.MyProxy;
import com.traffic.proxy.ProxyService;

public class TestTwo {
	public static void main(String[] args) {
		MyProxy mp = new MyProxy();
		ProxyService.start();
		Thread t = null;
		for(int i=0;i<100;i++){
				mp.changeWithcheck();
				String word = "ËÕÖÝÎÅµÀ";
				System.out.println(word);
				CloseableHttpClient httpClient =  MyHttpClientBuilder.build(mp.host, mp.port,null);
				HttpGet httpGet = new HttpGet();
				try {
					httpGet.setURI(UriBuilder.getURI(HomePage.scheme, HomePage.host, HomePage.path, HomePage.parameter));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			
				MyHttpGetBuilder.addHeader(HomePage.headers, httpGet);
				MyRespone homeReponse = GetSender.sendGet(httpGet, httpClient);
				if(homeReponse.getHttpClientContext().getResponse()==null){
					System.out.println("ccc");
					i--;
					continue;
				}
			try{
				t = new Thread(new TgifSend(homeReponse,mp));
				t.start();
				Tcre1 tr1 = new Tcre1(homeReponse);
				CloseableHttpClient httpClientTr1 =  MyHttpClientBuilder.build(mp.host, mp.port,tr1.cookieStore);
				HttpGet httpGetTr1 = new HttpGet(tr1.getURI());
				MyRespone tr1Response = GetSender.sendGet(httpGetTr1, httpClientTr1);
				Tcre2 tr2 = new Tcre2(tr1Response,homeReponse);
				CloseableHttpClient httpClientTr2 =  MyHttpClientBuilder.build(mp.host, mp.port,tr2.cookieStore);
				HttpGet httpGetTr2 = new HttpGet(tr2.getURI());
				MyHttpGetBuilder.addHeader(tr1.headers,httpGetTr2);
				MyRespone tr2Response = GetSender.sendGet(httpGetTr2, httpClientTr2);
				Tcre3 tr3 = new Tcre3(tr2Response,homeReponse);
				CloseableHttpClient httpClientTr3 =  MyHttpClientBuilder.build(mp.host, mp.port,tr3.cookieStore);
				HttpGet httpGetTr3 = new HttpGet(tr3.getURI());
				MyHttpGetBuilder.addHeader(tr2.headers,httpGetTr3);
				MyRespone tr3Response = GetSender.sendGet(httpGetTr3, httpClientTr3);
				PreWord preWord = new PreWord(tr3Response, homeReponse, word);
				CloseableHttpClient httpClientPre =  MyHttpClientBuilder.build(mp.host, mp.port,preWord.cookieStore);
				HttpGet httpGetPre = new HttpGet(preWord.getURI());
				MyHttpGetBuilder.addHeader(preWord.headers,httpGetPre);
				
				MyRespone preResponse = GetSender.sendGet(httpGetTr3, httpClientPre);
				
				QueryOne queryone = new QueryOne(preResponse, homeReponse, word);
				CloseableHttpClient httpClientq1 =  MyHttpClientBuilder.build(mp.host, mp.port,queryone.cookieStore);
				HttpGet httpGetq1 = new HttpGet(queryone.getURI());
				MyHttpGetBuilder.addHeader(queryone.headers,httpGetq1);
				MyRespone q1Response = GetSender.sendGet(httpGetq1, httpClientq1);
				
				QueryTwo query2 = new QueryTwo(q1Response, homeReponse, word);
				CloseableHttpClient httpClientq2 =  MyHttpClientBuilder.build(mp.host, mp.port,query2.cookieStore);
				HttpGet httpGetq2 = new HttpGet(query2.getURI());
				MyHttpGetBuilder.addHeader(query2.headers,httpGetq2);
				GetSender.sendGet(httpGetq2, httpClientq2);
				
				while(homeReponse.flag==0){
					System.out.println("w");
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				homeReponse.flag = 0;
				System.out.println("ok");
			}catch(Exception e){
				i--;
				homeReponse.flag = 1;
			}
			
		}
		
	}
	

	
}
