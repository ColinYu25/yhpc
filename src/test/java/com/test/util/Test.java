package com.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.params.CookiePolicy;

public class Test {
	
	public static String QY_URL = "http://127.0.0.1:8080/nbyhpc/";

	public void EmailImporter(String email, String password, String encoding) {
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		client.getParams().setParameter("http.protocol.single-cookie-header", true);
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String cookies, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Cookie",cookies);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
    
    public static void main(String[] args) {
//    	发送 GET 请求
//    	String s=sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
//    	System.out.println(s);
    	String str=sendPost(QY_URL+"app/login.mobile", "", "username=330200400023777&password=123456&type=1");
//    	String str=sendPost(QY_URL+"app/login.mobile", "", "username=anwei&password=ajj123456&type=2");
//    	企业信息
//    	String str=sendPost(QY_URL+"app/company_show.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id4387$qiye$edfd8920f886416296d7427cdea0ccef", "company.id=100841");
//    	企业列表
//    	String str=sendPost(QY_URL+"app/company_show.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id439400$qiye$5d0e300e6eb548ada4ca57d83ee7870a", "pagination.pageSize=10&pagination.itemCount=0");
//    	String str=sendPost(QY_URL+"app/company_save.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id439400$qiye$5d0e300e6eb548ada4ca57d83ee7870a", "companyVo.id=100841&companyVo.fddelegate=宁波特灵通金属制品有限公司1");
//    	String str=sendPost(QY_URL+"app/danger_list.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id4387$qiye$20d69b85e6fe4db996331cbbbdc895da", "company.id=100841&pagination.pageSize=10&pagination.itemCount=0&pagination.totalCount=10");
//    	String str=sendPost(QY_URL+"app/danger_show.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id439400$qiye$5d0e300e6eb548ada4ca57d83ee7870a", "daNomalDanger.id=2907819");
//    	String str=sendPost(QY_URL+"app/danger_save.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id439400$qiye$13eb9f755d9e42e3bdfb110507c896ae", "company.id=100841&daNomalDanger.id=2907822&daNomalDanger.description=aabbbb&daNomalDanger.hazardSourceCode=NP_1&daNomalDanger.secondType=1584194&daNomalDanger.governMoney=1&daNomalDanger.repaired=false&daNomalDanger.type=1332");
//    	String str=sendPost(QY_URL+"app/companyRemind.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id439400$qiye$62ebdf6d1b124182935b4a15e0727120", "company.id=100841");
//    	String str=sendPost(QY_URL+"app/msg_getVersion.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id4387$qiye$20d69b85e6fe4db996331cbbbdc895da", "os=android");
//    	String str=sendPost(QY_URL+"app/company_list.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id430371$anwei$999abef02ad7463eb452638d08feb52c", "");
//    	String str=sendPost(QY_URL+"app/danger_list.mobile", "SAFETYS_CLIENT_AUTH_KEY_NBYHPC=id430371$anwei$999abef02ad7463eb452638d08feb52c", "company.id=2906622&pagination.pageSize=10&pagination.itemCount=0");
    	System.out.println(str);
    }
}
