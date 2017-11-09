package test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class TestClient1 {

	public static String httpGet(String getUrl) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(getUrl);
		HttpResponse response = httpclient.execute(httpget);
		String content = "";
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				content = EntityUtils.toString(entity, HTTP.UTF_8);
			}
		}
		httpget.abort();
		return content;
	}

	public static void main(String[] args) throws IllegalStateException, IOException {
//		System.out.println(httpGet("http://60.190.2.243:7011/nbyhpc/statistic/nosecuritycheck/loadDangersTimeOutJson.xhtml?num=330212000325656"));
//		System.out.println(httpGet("http://60.190.2.247:8086/nbyhpc/statistic/nosecuritycheck/loadDangersTimeOutJson.xhtml?num=330212000325656"));
	}

}
