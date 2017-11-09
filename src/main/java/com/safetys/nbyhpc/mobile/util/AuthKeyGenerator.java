package com.safetys.nbyhpc.mobile.util;

/**
 * 移动客户端验证密钥
 *
 */
public class AuthKeyGenerator {
	
	public static String generate(Long customId) {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return ("id" + (customId == null ? 0 : customId.longValue()) + "$" + uuid.toString().replaceAll("[-]", ""));
	}

	public static String generate(Long customId, String code) {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return ("id" + (customId == null ? 0 : customId.longValue()) + "$" + code + "$" + uuid.toString().replaceAll("[-]", ""));
	}

	public static Long extractId(String key) {
		try {
			return Long.valueOf(key.split("[$]")[0].replace("id", ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * userIndustry
	 * @param key
	 * @return
	 */
	public static String extractCode(String key) {
		try {
			return String.valueOf(key.split("[$]")[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args){
		String key = generate(111L, "ROLE_ADMIN");
		System.out.println(key);
		System.out.println(extractId(key));
		System.out.println(extractCode(key));
	}
	
}
