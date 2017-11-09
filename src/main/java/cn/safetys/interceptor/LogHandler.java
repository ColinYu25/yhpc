package cn.safetys.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class LogHandler {

	private static Log logger = LogFactory.getLog(LogHandler.class);

	@SuppressWarnings("unused")
	private void printLog(JoinPoint joinPoint) {
		StringBuffer log = new StringBuffer();
		StringBuffer s = new StringBuffer();
		String mname = "";
		Object[] args = joinPoint.getArgs();

		if (args.length != 0) {
			for (int i = 0; i < args.length; i++) {
				s.append(args[i].getClass()).append(" ").append(args[i]).append(",");
			}
			s.deleteCharAt(s.length() - 1);
		}

		mname = joinPoint.getSignature().getName();
		log.append(mname).append("(").append(s).append(")");

		logger.info(log);
	}
}
