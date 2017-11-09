package cn.safetys.interceptor;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.MDC;

import cn.safetys.annotation.AnnotationUtils;

public class NameMatchMethodInterceptor implements MethodInterceptor {

	private static Log logger = LogFactory.getLog(NameMatchMethodInterceptor.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {

		long t1, t2;
		StringBuffer log = new StringBuffer();
		StringBuffer s = new StringBuffer();
		String mname = "";
		Object[] args = invocation.getArguments();

		if (args.length != 0) {
			for (int i = 0; i < args.length; i++) {
				s.append(args[i].getClass()).append(" ").append(args[i]).append(",");
			}
			s.deleteCharAt(s.length() - 1);
		}
		// mname = invocation.getMethod().getName();
		mname = invocation.getMethod().toGenericString();

		// log.append(mname).append("(").append(s).append(")");
		String describs = AnnotationUtils.getMethodAnnotation(invocation.getMethod());
		if (describs == null || "".equals(describs)) {
			log.append(mname);
		} else {
			log.append(mname).append("[").append(describs).append("]");
		}
		String parameterdescribs = AnnotationUtils.getMethodParameterAnnotation(invocation.getMethod());
		if (parameterdescribs == null || "".equals(parameterdescribs)) {
			log.append("(").append(s).append(")");
		} else {
			log.append("(").append(s).append("[").append(parameterdescribs).append("]").append(")");
		}

		t1 = new Date().getTime();

		Object returnObject = invocation.proceed();

		t2 = new Date().getTime();

		MDC.put("time", t2 - t1);
		logger.info(log);

		return returnObject;
	}

}
