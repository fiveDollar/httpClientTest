package HttpClientUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

public class UriBuilder {
	public static URI getURI(String scheme,String host,String path,String[] parameter) throws URISyntaxException{
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme(scheme);
		uriBuilder.setHost(host);
		if(path!=null)
			uriBuilder.setPath(path);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		if(parameter!=null&&parameter.length>0){
			for (int i = 0; i < parameter.length; i++) {
//				System.out.println(parameter[i]);
				if(!parameter[i].contains("="))
					continue;
//				System.out.println(parameter[i]);
				if(parameter[i].split("=").length!=2){
					continue;
				}
				NameValuePair nvp = new BasicNameValuePair(parameter[i].split("=")[0], parameter[i].split("=")[1]);
				parameters.add(nvp);
			}
		}
		uriBuilder.addParameters(parameters);
		return uriBuilder.build();
	}
	
	public static URI getURI(String scheme,String host,String path,List<NameValuePair> parameters) throws URISyntaxException{
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme(scheme);
		uriBuilder.setHost(host);
		if(path!=null)
			uriBuilder.setPath(path);
		uriBuilder.addParameters(parameters);
		return uriBuilder.build();
	}
}
