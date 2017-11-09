package com.safetys.framework.web.tag.pagination;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.safetys.framework.domain.model.pagination.Pagination;

/**
 * @author:seak.lv
 * @MSN:seak82@hotmail.com
 * @Email:<a href="mailto:seaklv@sina.com">seaklv@sina.com</a>
 * @created:2008-3-10
 * @modified:2009-11-18
 */
public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = 8070980605664636856L;
	private Pagination pagination;
	HttpSession session;

	public PaginationTag() {
		/* empty */
	}

	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(getFormHidden() + getNavigatorBar());
			session = pageContext.getSession();
		} catch (IOException ioe) {
			// throw new JspException(ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		HttpSession session = pageContext.getSession();
		if (session != null) {
			Object requestUrl = session.getAttribute("request.full.url");

			if (requestUrl != null) {// 1.01(含）以下版本兼容
				session.setAttribute("redirect.url", requestUrl);
			} else {// 用新的cookie方式记录,支持嵌套分页和多线程浏览器窗口（ext）
				try {
					setRequestURLToSession();
				} catch (UnsupportedEncodingException e) {
					/* empty body */
				}
			}
		}
		return EVAL_PAGE;
	}

	private String getFormHidden() {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script language='javascript'>\n");
		buffer.append("function paginationSubmit(event,submitType){\n");
		buffer.append("event=event?event:(window.event?window.event:null);\n");
		buffer.append("if (event.keyCode==13){\n");
		buffer.append("if(submitType=='page'){toPage();}else if(submitType=='pageSize'){toPageSize();}");
		buffer.append("}}");
		buffer.append("function toPage(){ \n");
		buffer.append("var reg=/^[0-9]*$/;\n");
		buffer.append("var paNewPageSize=document.getElementById('paNewPageSize').value;\n");
		buffer.append("var isCurrent=reg.exec(paNewPageSize);");
		buffer.append("if(!isCurrent||paNewPageSize<=0){");
		buffer.append("   alert('\u8F93\u5165\u683C\u5F0F\u9519\u8BEF\uFF0C\u5E94\u4E3A\u5927\u4E8E\u96F6\u7684\u6B63\u6574\u6570！');");
		buffer.append("   document.getElementById('paNewPageSize').value="
				+ pagination.getCurrentPage() + ";");
		buffer.append("   return false;");
		buffer.append("}else if(paNewPageSize==" + pagination.getCurrentPage()
				+ "){");
		buffer.append("   return false;");
		buffer.append("}else if(paNewPageSize>" + pagination.getTotalPage()
				+ "){");
		buffer.append("   alert('\u8F93\u5165\u503C\u4E0D\u80FD\u5927\u4E8E\u6700\u5927\u9875\u6570！');");
		buffer.append("   return false;");
		buffer.append("}");
		buffer.append("document.getElementById('paItemCount').value=(eval(paNewPageSize-1))*"
				+ pagination.getPageSize() + ";");
		buffer.append("document.forms['navigationForm'].submit();");
		buffer.append("return false;");
		buffer.append("}\n");
		buffer.append("function toPageSize(){ \n");
		buffer.append("var reg=/^[0-9]*$/;\n");
		buffer.append("var pageSizeValue=document.getElementById('paPageSize').value;\n");
		buffer.append("var isCurrent=reg.exec(pageSizeValue);");
		buffer.append("if(!isCurrent||pageSizeValue<=0){");
		buffer.append("   alert('\u8F93\u5165\u683C\u5F0F\u9519\u8BEF\uFF0C\u5E94\u4E3A\u5927\u4E8E\u96F6\u7684\u6B63\u6574\u6570！');");
		buffer.append("   document.getElementById('paPageSize').value="
				+ pagination.getPageSize() + ";");
		buffer.append("   return false;");
		buffer.append("}else if(pageSizeValue==" + pagination.getPageSize()
				+ "){");
		buffer.append("   return false;");
		buffer.append("}else if(pageSizeValue>200){");
		buffer.append("   alert('\u6BCF\u9875\u663E\u793A\u7684\u6761\u6570\u4E0D\u80FD\u5927\u4E8E200\u6761\uFF01');");
		buffer.append("   return false;");
		buffer.append("}");
		buffer.append("document.forms['navigationForm'].submit();");
		buffer.append("return false;");
		buffer.append("}");
		buffer.append("</script>\n");

		buffer.append("<style type='text/css'>\n");
		buffer.append("#pagination{line-height:20px;color:#000;font-size:13px;}\n");
		buffer.append("#pagination a{text-decoration:none;color:#000;}\n");
		buffer.append("#pagination a:hover{text-decoration:underline;}\n");
		buffer.append("#pagination input{text-align:center;border-right: #ffffff 0px solid;border-top: #ffffff 0px solid;font-size: 9pt; border-left: #ffffff 0px solid;border-bottom: #c0c0c0 1px solid;background-color: #ffffff}\n");
		buffer.append("#pagination select{border-right: #000000 1px solid;border-top: #ffffff 1px solid;font-size: 12px; border-left: #ffffff 1px solid;color:#003366;border-bottom: #000000 1px solid;background-color: #f4f4f4;}");
		buffer.append("\n</style>\n");
		buffer.append("<form action='?' method='post' name='navigationForm'>\n");
		for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
			String name = (String) en.nextElement();
			String value = request.getParameter(name);
			value = value == null ? "" : value;
			if (!name.equals("pagination.totalCount")
					&& !name.equals("pagination.pageSize")
					&& !name.equals("pagination.itemCount")) {
				buffer.append("<input type='hidden' name='" + name
						+ "' value='" + value + "'>\n");
			}
		}
		buffer.append("<input type='hidden' id='paTotalCount' name='pagination.totalCount' value='"
				+ pagination.getTotalCount() + "'>\n");
		buffer.append("<input type='hidden' id='paItemCount' name='pagination.itemCount' value='"
				+ pagination.getItemCount() + "'>\n");
		return buffer.toString();
	}

	private String getNavigatorBar() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div id='pagination'>\u5171"
				+ pagination.getTotalCount() + "\u6761，");// 当前显示"+pagination.getStartCount()+"-"+pagination.getEndCount()+"条，
		buffer.append("\u6BCF\u9875<input size='1' type='text' id='paPageSize' name='pagination.pageSize' value='"
				+ pagination.getPageSize()
				+ "' onChange='toPageSize();' onKeyDown='paginationSubmit(event,\"pageSize\");' />\u6761，\r\n");
		buffer.append("\u5171" + pagination.getTotalPage() + "\u9875，");
		buffer.append("\u9875\u6B21" + pagination.getCurrentPage() + "/"
				+ pagination.getTotalPage() + "\u9875，");
		if (pagination.hasPreviousPage()) {
			buffer.append("<a href='javascript:void(0)' class='paginationIndex' onClick=\"javascript:document.getElementById('paItemCount').value='0';document.navigationForm.submit();return false;\">\u9996\u9875</a>&nbsp;&nbsp;&nbsp;&nbsp;  \r\n ");
			buffer.append("<a href='javascript:void(0)' class='paginationIndex' onClick=\"javascript:document.getElementById('paItemCount').value='"
					+ (pagination.getItemCount() - pagination.getPageSize())
					+ "';document.navigationForm.submit();return false;\">\u4E0A\u9875</a>&nbsp;&nbsp;&nbsp;&nbsp;   \r\n ");
		}
		if (pagination.hasNextPage()) {
			buffer.append("<a href='javascript:void(0)' class='paginationIndex' onClick=\"javascript:document.getElementById('paItemCount').value='"
					+ (pagination.getItemCount() + pagination.getPageSize())
					+ "';document.navigationForm.submit();return false;\">\u4E0B\u9875</a>&nbsp;&nbsp;&nbsp;&nbsp;    \r\n");
			buffer.append("<a href='javascript:void(0)' class='paginationIndex' onClick=\"javascript:document.getElementById('paItemCount').value='"
					+ (pagination.getTotalPage() - 1)
					* pagination.getPageSize()
					+ "';document.navigationForm.submit();return false;\">\u5C3E\u9875</a>&nbsp;  \r\n");
		}
		buffer.append("&nbsp;&nbsp;\u8DF3\u8F6C\u5230\u7B2C<input size='1' id='paNewPageSize' value='"
				+ pagination.getCurrentPage()
				+ "' onChange='toPage();' onKeyDown='paginationSubmit(event,\"page\");' />\u9875\uFF08\u6309Enter\u952E\uFF09\r\n");
		buffer.append("</div>\n</form>\n");
		// buffer.append("<script language='javascript'>document.getElementById('paPageSize').value="+pagination.getPageSize()+";</script>\n");
		return buffer.toString();
	}

	/**
	 * @description 记录或追加分页URL记录
	 */
	@SuppressWarnings("unchecked")
	private void setRequestURLToSession() throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		Object paginationMap = session.getAttribute("pagination_url_map");
		if (paginationMap != null) {
			map = (Map<String, String>) paginationMap;
		}
		HttpServletResponse response = (HttpServletResponse) pageContext
				.getResponse();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String requestURI = request.getRequestURI();
		String key = requestURI.substring(requestURI.lastIndexOf("/") + 1);
		String requestURL = response.encodeURL(getRequestURL(request));
		map.put(key, requestURL);
		session.setAttribute("pagination_url_map", map);
	}

	/**
	 * @param request
	 * @return 请求URL的全路径
	 */
	private String getRequestURL(HttpServletRequest request)
			throws UnsupportedEncodingException {
		StringBuffer requestURL = new StringBuffer(request.getRequestURL());
		Map parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			requestURL.append("?");
			Set set = parameters.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					// 因持久层中域model对象中有带默认值的createTime和modifyTime属性，所以空值也进行url传递，用于在搜索时清空默认值,否则搜索框中会显示默认值
					if ("createTime".equals(key) || "modifyTime".equals(key)
							|| (value != null && !"".equals(value))) {
						requestURL.append(key).append("=")
								.append(URLEncoder.encode(value, "UTF-8"))
								.append("&");
					}
				}
			}
			requestURL.deleteCharAt(requestURL.length() - 1);
		}
		return requestURL.toString();

	}

	public void setPagination(Object object) {
		if (object == null) {
			pagination = new Pagination();
		} else {
			pagination = (Pagination) object;
		}
	}
}
