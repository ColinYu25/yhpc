package cn.safetys.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtils {

	public static String getMethodAnnotation(Method method) {
		StringBuffer describSb = new StringBuffer();
		if (method.isAnnotationPresent(Field_Method_Parameter_Annotation.class)) {
			Field_Method_Parameter_Annotation ma = method.getAnnotation(Field_Method_Parameter_Annotation.class);
			describSb.append("   " + ma.describ()); // 获得方法描述
			describSb.append("   " + ma.type()); // 获得方法类型
		}

		return describSb.toString();
	}

	public static String getMethodParameterAnnotation(Method method) {

		StringBuffer parameterDescribSb = new StringBuffer();

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int j = 0; j < parameterAnnotations.length; j++) {
			int length = parameterAnnotations[j].length;
			if (length == 0) {
				System.out.println("没有添加Annotation参数");
			} else {
				for (int k = 0; k < length; k++) {
					// 获得指定的注释：
					Field_Method_Parameter_Annotation pa = (Field_Method_Parameter_Annotation) parameterAnnotations[j][k];
					parameterDescribSb.append(" " + pa.describ()); // 获得参数描述
					parameterDescribSb.append(" " + pa.type()); // 获得参数类型
				}
			}
		}

		return parameterDescribSb.toString();
	}
}
