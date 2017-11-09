package cn.safetys.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 通用类
 * 
 * @author yinghui.zhang
 * @E-mail uuhui@163.com
 * @date 创建时间: 2013年9月30日
 * @Description: 通用类
 * @Modify 修改人：
 * @date 修改时间：
 * @modifyContent
 */
public class CommonUtil {
	/**
	 * 枚举表字段属性map集合
	 */
	static Map<String, String> TENUMMAP = null;

	// 静态代码块
	static {
		TENUMMAP = new HashMap<String, String>();
		// 添加枚举表字段属性
		TENUMMAP.put("CODE", "code");
		TENUMMAP.put("NAME", "name");
		TENUMMAP.put("SORT_NUM", "orderNo");
		TENUMMAP.put("pCode", "pCode");
		/*
		 * // 添加区域内表字段属性 TENUMMAP.put("AREA_NAME", "name");
		 * //PARAMMAP.put("AREA_CODE", "code"); TENUMMAP.put("SHORT_NAME",
		 * "shortName"); TENUMMAP.put("ENGLISH_NAME", "englishName");
		 */
	}

	/**
	 * 将json字符串动态转换成Bean 利用java反射技术给bean属性赋值
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param t
	 *            javaBean 类
	 * @return javaBean 类
	 * @throws Exception
	 *             java 异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T JsonToBean(String jsonString, T t) throws Exception {
		Map<String, String> map = null;
		Map<String, String> m = null;
		// 将json字符串转换成JSONObject对象
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		// 将jsonObject转换成Map对象
		map = (Map<String, String>) jsonObject;
		if (map.get("info") == null)
			m = map;
		else
			// 获取map对象中的key为Data的json字符串内容
			m = (Map<String, String>) JSONObject.fromObject(map.get("data"));
		if (m.size() == 0)
			t = null;
		else {
			Field[] fields = t.getClass().getDeclaredFields();
			// 动态给bean对象属性赋值
			String type = null;
			String value = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase("serialVersionUID"))
					continue;
				else {
					field.setAccessible(true);
					type = field.getType().getName();
					value = m.get(field.getName());
					if (type.equals("java.lang.String")) {
						field.set(t, value == null || "".equals((String) value) ? null : value);
					} else if (type.equals("java.lang.Long")) {
						field.set(t, value == null || "".equals((String) value) ? null : Long.parseLong(value));
					} else if (type.equals("java.util.Date")) {
						field.set(t, value == null || "".equals((String) value) ? null : sdf.parse(value));
					} else if (type.equals("java.lang.Double")) {
						field.set(t, value == null || "".equals((String) value) ? null : Double.parseDouble(value));
					} else if (type.equals("java.lang.Integer")) {
						field.set(t, value == null || "".equals((String) value) ? null : Integer.parseInt(value));
					}
				}
			}
		}
		return t;
	}

	/**
	 * json转换动态bean集合
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param t
	 *            动态类
	 * @return List集合
	 * @throws Exception
	 *             异常
	 */
	/**
	 * @param jsonStr
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> JsonToListBean(String jsonStr, Object t) throws Exception {
		List<Object> list = new ArrayList<Object>();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		// 将jsonObject转换成Map对象
		jsonObject = JSONObject.fromObject(jsonObject.get("data"));
		Object obj = jsonObject.get("DATA_SET");
		if (obj instanceof JSONArray) {
			Iterator<JSONObject> it = ((JSONArray) obj).iterator();
			while (it.hasNext()) {
				JsonToBean(it.next().toString(), t.getClass().newInstance(), null, list);
			}
		}
		return list;
	}

	/**
	 * json数据转换动态bean
	 * 
	 * @param jsonStr
	 *            json字符吕
	 * @param t
	 *            动态戊
	 * @param pCode
	 *            上级代码
	 * @param returnList
	 *            返回List集合类
	 * @throws Exception
	 *             异常
	 */
	@SuppressWarnings("unchecked")
	public static void JsonToBean(String jsonStr, Object t, String pCode, List<Object> returnList) throws Exception {
		String parentCode = null;
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			if (k.toString().equalsIgnoreCase("CODE")) {
				parentCode = (String) json.get(k);
			}
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				pCode = parentCode;
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JsonToBean(it.next().toString(), t.getClass().newInstance(), pCode, returnList);
				}
			} else {
				if (k.toString().equalsIgnoreCase("CODE")) {
					t = setPropertyValue(t, json, pCode);
					returnList.add(t);
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * 根据java反射技术动态给动态类的属性赋值
	 * 
	 * @param t
	 *            动态实体类
	 * @param json
	 *            json数据
	 * @param k
	 *            json中的键
	 * @param parentCode
	 *            上级代码
	 * @return t 动态实体对象
	 * @throws Exception
	 *             异常
	 */
	private static <T> T setPropertyValue(T t, JSONObject json, String parentCode) throws Exception {
		// 设置子类属性值
		if (parentCode != null) {
			Field fieldParentCode = t.getClass().getDeclaredField("pCode");
			fieldParentCode.setAccessible(true);
			fieldParentCode.set(t, parentCode);
		}
		Field[] fields = t.getClass().getSuperclass().getDeclaredFields();
		// 动态给bean对象属性赋值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = null;// 定义键变量
		Object fieldType = null;// 字义字段类型
		Object fieldValue = null;// 定义字段值变量
		for (Field field : fields) {
			// 类属性是否与定义字段属性集合中
			if (TENUMMAP.containsValue(field.getName())) {
				field.setAccessible(true);
				fieldType = field.getType().getName();
				Iterator<Entry<String, String>> iterator = TENUMMAP.entrySet().iterator();
				Entry<String, String> entry = null;
				while (iterator.hasNext()) {
					entry = iterator.next();
					// 判断字段属性是否存在于集合的值当中
					if (entry.getValue().equalsIgnoreCase(field.getName())) {
						key = entry.getKey();
						break;
					}
				}
				fieldValue = json.get(key);
				if (fieldType.equals("java.lang.String")) {
					field.set(t, fieldValue == null || "".equals((String) fieldValue) ? null : (String) fieldValue);
				} else if (fieldType.equals("java.lang.Long")) {
					field.set(t, fieldValue == null ? null : Long.parseLong(fieldValue + ""));
				} else if (fieldType.equals("java.util.Date")) {
					field.set(t, fieldValue == null || "".equals((String) fieldValue) ? null : sdf.parse(fieldValue + ""));
				} else if (fieldType.equals("java.lang.Double")) {
					field.set(t, fieldValue == null || "".equals((String) fieldValue) ? null : Double.parseDouble((String) fieldValue));
				} else if (fieldType.equals("java.lang.Integer")) {
					field.set(t, fieldValue == null || "".equals((String) fieldValue) ? null : Integer.parseInt((String) fieldValue));
				}
			}

		}
		return t;
	}

	/**
	 * 通过反射获取属性值
	 * 
	 * @param entity
	 *            实体对象
	 * @param propertyName
	 *            属性名称
	 * @return 属性值
	 * @throws Exception
	 *             异常
	 */
	public static Object getPropertyValue(Object entity, String propertyName) throws Exception {
		Field field = null;
		String clazzName = entity.getClass().getName();
		if (propertyName.equalsIgnoreCase("id")) {
			if (clazzName.equalsIgnoreCase("com.safetys.nbyhpc.domain.model.TCompany") 
					//|| clazzName.equalsIgnoreCase("com.safetys.nbyhpc.domain.model.Publicity") ||
					//clazzName.equalsIgnoreCase("com.safetys.nbyhpc.domain.model.GradeChangeInfo")
					) {
				field = entity.getClass().getDeclaredField(propertyName);
			} else {
				field = entity.getClass().getSuperclass().getSuperclass().getDeclaredField(propertyName);
			}
		} else {
			field = entity.getClass().getDeclaredField(propertyName);
		}
		field.setAccessible(true);
		Object obj = null;
		String fieldType = field.getType().getName();
		obj = field.get(entity);
		if (fieldType.equalsIgnoreCase("java.util.Date")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (obj != null)
				return sdf.format((Date) obj);
			else
				return null;
		} else
			return obj;
	}

	/**
	 * 解析json字符串中的Msg内容
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return msg内容f
	 */
	public static boolean getJsonMsg(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Object obj = jsonObject.get("info");
		if (obj != null) {
			jsonObject = JSONObject.fromObject(obj);
			return ((String) jsonObject.get("success")).trim().equalsIgnoreCase("true");
		} else
			return false;
	}

	/**
	 * 加载配置properties配置文件
	 * 
	 * @return Properties
	 */
	public static Properties parseProperties() {
		InputStream inputStream = null; // 文件流
		Properties prop = null;
		try {
			// 创建文件流以文件流形式加载properties文件
			inputStream = CommonUtil.class.getClassLoader().getResourceAsStream("common.properties");
			prop = new Properties();
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	/**
	 * 获取properties中的键值对信息
	 * 
	 * @param context
	 * @return RequestMetadata
	 */
	public static String getPropValue(Properties prop, String key) {
		return prop.getProperty(key);
	}

	/**
	 * 根据文件路径删除文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return true/false
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param path
	 *            文件夹路径
	 * @return　true/false
	 */
	public static boolean deleFloder(String path) {
		boolean flag = false;
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			if (file.isDirectory()) {
				file.delete();
			}
		}
		return flag;
	}
}
