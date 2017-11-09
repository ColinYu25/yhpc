package com.safetys.nbyhpc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ognl.DefaultTypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateConverter extends DefaultTypeConverter {

	private static final Log logger = LogFactory.getLog(DateConverter.class);

	private static final String CHINA_DATE_PATERN = "yyyy 年 MM 月 dd 日";

	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	private static final String MONTH_PATTERN = "yyyy-MM";

	/**
	 * Convert value between types
	 */
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if (toType == Date.class) {
			result = doConvertToDate(value);
		} else if (toType == String.class) {
			result = doConvertToString(value);
		}
		return result;
	}

	/**
	 * Convert String to Date
	 * 
	 * @param value
	 * @return
	 */
	private Date doConvertToDate(Object value) {
		Date result = null;

		if (value instanceof String) {

			// TODO add date converter parse order here
			result = DateUtils.str2Date((String) value, new String[] { CHINA_DATE_PATERN, DATE_PATTERN, MONTH_PATTERN });

			// all patterns failed, try a milliseconds constructor
			if (result == null && StringUtils.isNotEmpty((String)value)) {

				try {
					result = new Date(new Long((String) value).longValue());
				} catch (Exception e) {
					logger.error("Converting from milliseconds to Date fails!", e);
				}
			}else{
				return result;
			}

		} else if (value instanceof Object[]) {
			// let's try to convert the first element only
			Object[] array = (Object[]) value;

			if ((array != null) && (array.length >= 1)) {
				value = array[0];
				result = doConvertToDate(value);
			}

		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}
		return result;
	}

	/**
	 * Convert Date to String
	 * 
	 * @param value
	 * @return
	 */
	private String doConvertToString(Object value) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CHINA_DATE_PATERN);
		String result = null;
		if (value instanceof Date) {
			result = simpleDateFormat.format(value);
		}
		return result;
	}
}
