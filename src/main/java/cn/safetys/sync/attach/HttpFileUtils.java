package cn.safetys.sync.attach;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpFileUtils {
	private static final Log log = LogFactory.getLog(HttpFileUtils.class);
	
	public static void download(String url,String filePath){
		download(url, new File(filePath));
	}
	
	public static void download(String url,File file){
		try {
			FileUtils.copyURLToFile(new URL(url), file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info("下载文件出错"+file.getPath(), e);
		} catch (IOException e) {
			e.printStackTrace();
			log.info("下载文件出错"+file.getPath(), e);
		}
	}
	
	public static boolean upload(String url, String filePath,String serverSavePath) {
		return upload(url, new File(filePath), serverSavePath);
	}
	
	public static boolean upload(String url, File file,String serverSavePath) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(file);
			StringBody comment = new StringBody(serverSavePath);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", bin);// file1为请求后台的File upload;属性
			reqEntity.addPart("savePath", comment);// filename1为请求后台的普通参数;属性
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				log.info("服务器正常响应.....");
				HttpEntity resEntity = response.getEntity();
				log.info(EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
				log.info(resEntity.getContent());
				EntityUtils.consume(resEntity);
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.info("上传文件出错 "+file.getPath(), e);
		} catch (IOException e) {
			e.printStackTrace();
			log.info("上传文件出错 "+file.getPath(), e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {

			}
		}
		return false;
	}
	
	public static void main(String[] args) {
//		String url = "https://www.baidu.com/img/270gif_716895d1fbe568ab4b3a95f99696f34a.gif";
//		String save = "C:\\Users\\HelloFR\\Desktop\\a\\a\\a.gif";
//		download(url, save);
//		System.out.println("OK");
		String url = "http://127.0.0.1:2000/attach2/yhbzxkz/upload.sj";
		upload(url, "C:\\Users\\HelloFR\\Desktop\\6ef3e2begw1ett40fyf8lg209l067qv7.gif","update/a.jdp");
//		System.out.println("OK");
		
	}
	
	

}
