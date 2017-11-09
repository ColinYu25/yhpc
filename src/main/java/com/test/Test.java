package com.test;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

public class Test {
	// public static boolean get() {
	// try {
	// return false;
	// } finally {
	// return true;
	// }
	// }
	// public void test1() {
	// String a = "bob";
	// a.replace('b', 'p');
	// if(a.equals("pop")){
	// System.out.println(a);
	// }
	// }
	// private List actions;
	// public void Thing(String startingActions) {
	// StringTokenizer tokenizer = new StringTokenizer(startingActions);
	// while (tokenizer.hasMoreTokens()) {
	// actions.add(tokenizer.nextToken());
	// }
	// }
	// public static void main(String[] args) {
	// String classPath = "java.lang.String";
	// System.out.println(classPath.replaceAll("[.]", "/"));
	//        
	// int id=100;
	// int i =101;
	// int n = 100*id/i;
	// System.out.println(n);
	// }
	// 请大家思考一个问题，return的具体实现是什么？如果返回false，为什么；如果返回是true，又是为什么呢？给个你认为正确答案的理由出来，这才是考题的目的
	// public static void main(String[] args) {
	// int j = 0;
	// for (int i = 0; i < 100; i++)
	// j=j++;
	// System.out.println(j);
	// }

	// public static void main(String[] args) {
	// Calendar cal = Calendar.getInstance();
	// cal.set(1999, 12, 31); // Year, Month, Day
	// System.out.print(cal.get(Calendar.YEAR) + " ");
	//	  
	// Date d = cal.getTime();
	// System.out.println(d.getDay());
	// }
	//	
	// public static void main(String[] args) {
	// try {
	// System.out.println("Hello world");
	// System.exit(0);//你知道这句话真正含义之后这道题就很简单了
	// } finally {
	// System.out.println("Goodbye world");
	// }
	// }

	public static void merge(int[] a, int low, int mid, int high) {
		int[] b = new int[high - low + 1];
		int s = low;
		int t = mid + 1;
		int k = 0;
		while (s <= mid && t <= high) {
			if (a[s] <= a[t])
				b[k++] = a[s++];
			else
				b[k++] = a[t++];
		}
		while (s <= mid)
			b[k++] = a[s++];
		while (t <= high)
			b[k++] = a[t++];
		for (int i = 0; i < b.length; i++) {
			a[low + i] = b[i];
		}
	}
	public static void mergesort(int a[], int low, int high) {
		if (low < high) {
			mergesort(a, low, (low + high) / 2);
			mergesort(a, (low + high) / 2 + 1, high);
			merge(a, low, (high + low) / 2, high);
		}
	}
	/*
	 * public static void main(String[]args){ String sql = "select * from table
	 * id = :type"; sql = sql.replaceAll(":type", "1"); System.out.println(sql);
	 *  }
	 */

	public static void main(String[] args) throws Exception {

		md5(); // 使用简单的MD5加密方式

		sha_256(); // 使用256的哈希算法(SHA)加密

		sha_SHA_256(); // 使用SHA-256的哈希算法(SHA)加密

		md5_SystemWideSaltSource(); // 使用MD5再加全局加密盐加密的方式加密
	}

	public static void md5() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true
		// 表示：生成24位的Base64版
		md5.setEncodeHashAsBase64(false);
		String pwd = md5.encodePassword("123", null);
		System.out.println("MD5: " + pwd + " len=" + pwd.length());
	}

	public static void sha_256() {
		ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
		sha.setEncodeHashAsBase64(false);
		String pwd = sha.encodePassword("123", null);
		System.out.println("哈希算法 256: " + pwd + " len=" + pwd.length());
	}

	public static void sha_SHA_256() {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String pwd = sha.encodePassword("123",null);
		System.out.println("哈希算法 SHA-256: " + pwd + " len=" + pwd.length());
	}

	public static void md5_SystemWideSaltSource() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		// 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
		String pwd = md5.encodePassword("123", "acegisalt");
		System.out.println("MD5 SystemWideSaltSource: " + pwd + " len="
				+ pwd.length());
	}
}
