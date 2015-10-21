package HttpClientUtil;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;

public class MyHttpClientBuilder {
	public static CloseableHttpClient build(String proxyStr, int port,
			CookieStore cs) {

		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 1) {
					// Do not retry if over max retry count
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					// Timeout
					return false;
				}
				if (exception instanceof UnknownHostException) {
					// Unknown host
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					// Connection refused
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					return false;
				}
				HttpClientContext clientContext = HttpClientContext
						.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		
		ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(
			HttpResponse response,
			HttpContext context) {
			long keepAlive = super.getKeepAliveDuration(response, context);
			if (keepAlive == -1) {
			// Keep connections alive 5 seconds if a keep-alive value
			// has not be explicitly set by the server
			keepAlive = 5000;
			}
			return keepAlive;
			}
			};
			
		// CloseableHttpClient httpclient = HttpClients.custom()
		// .setRetryHandler(myRetryHandler).build();

		HttpHost proxy = new HttpHost(proxyStr, port);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(
				proxy);
		HttpClientBuilder httpClientBuilder = HttpClients.custom()
				.setRoutePlanner(routePlanner).setRetryHandler(myRetryHandler).setKeepAliveStrategy(keepAliveStrat);
		if (cs != null) {
			httpClientBuilder.setDefaultCookieStore(cs);
		}
		return httpClientBuilder.build();
	}
}
