package cn.safetys.sync.attach;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ResourceUtils;

import cn.safetys.sync.attach.HttpFileUtils;

/**
 * 文件过滤器
 * @author HelloFR
 *
 */
public class AttachFilter implements Filter {
	
	protected static final Log log = LogFactory.getLog(AttachFilter.class);
	
	protected static final String DEFAULT_CONFIG_FILE="classpath:system.properties";
	
	protected static final String SYSTEM_ATTACH_SERVER_URL_KEY="system.attach.server.url";
	
	protected static final String ATTACH_SERVER_CONFIG_KEY = "config";
	/**
	 * 文件服务地址
	 */
	private String attachServer;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String v = filterConfig.getInitParameter(SYSTEM_ATTACH_SERVER_URL_KEY);
		if(StringUtils.isNotBlank(v)){
			attachServer = StringUtils.trim(v);
		}else{
			v = filterConfig.getInitParameter(ATTACH_SERVER_CONFIG_KEY);
			File file = null;
			try {
				if(StringUtils.isNotBlank(v)){
					file = ResourceUtils.getFile(v);
				}else{
					file = ResourceUtils.getFile(DEFAULT_CONFIG_FILE);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if(file!=null&&file.exists()){
				Properties prop = new Properties();
				try {
					prop.load(new FileInputStream(file));
					attachServer = prop.getProperty(SYSTEM_ATTACH_SERVER_URL_KEY);
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = ((HttpServletRequest)req);
		 HttpServletResponse response = ((HttpServletResponse)res);
		 String currentUrl = request.getServletPath();//当前访问路径
		 if(currentUrl.endsWith(".do")||currentUrl.endsWith(".sj")){
			 chain.doFilter(req, res);
			 return ;
		 }
		 String realPath= request.getSession().getServletContext().getRealPath(currentUrl);//绝对路径
		 File f = new File(realPath);
		 if(f.isDirectory()){
			 return;
		 }
		 if(!f.exists()){
			 log.info("文件 "+currentUrl+" 不存在，前往文件服务器 "+attachServer+" 获取文件");
			 HttpFileUtils.download(this.getDownloadUrl(currentUrl), f);
			 log.info((f.exists()?"下载成功":"下载失败")+" "+currentUrl);
			 if(f.exists()){
				 response.setContentType("application/octet-stream");
				 response.setHeader("Content-Disposition","attachment;filename=" + f.getName());
				 FileUtils.copyFile(f, res.getOutputStream());
				 return;
			 }
		 }
		 log.info(request.getSession().getServletContext().getRealPath(currentUrl));
		 chain.doFilter(req, res);
	}
	
	private String getDownloadUrl(String url){
		return attachServer+url;
	}
	
	public void destroy() {

	}

	public String getFileServer() {
		return attachServer;
	}

}
