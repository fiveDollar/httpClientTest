package HttpClientUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.entity.BufferedHttpEntity;

public class EntityReader {
	public static String  ReadeEntity(BufferedHttpEntity bhe,String charSet){
		ByteArrayOutputStream bos = null;
		InputStream in = null;
		try {
			in = bhe.getContent();
			bos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024 * 1024];
			int size = in.read(temp);
			while(size>0){
				bos.write(temp, 0, size);
				try {
					size = in.read(temp);
				} catch (Exception e) {
					e.printStackTrace();
					size = 0;
				}
			}
			return bos.toString(charSet);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bos != null)
					bos.close();
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
