package com.test;

import java.util.Date;

import com.bjsxt.bean.Contact;
import com.bjsxt.bean.User;
import com.bjsxt.hibernate.MemCached;


public class MemCachedTest {

	public static void test1() {

		try {
			MemCached cache = MemCached.getInstance();
			
			cache.add("hello", 234);
			cache.replace("hello", 823456);
			System.out.println("hello:" + cache.get("hello"));// 永不过期

			cache.add("test", "This is a test String", new Date(20000));// 二十秒后过期
			System.out.println("test:" + cache.get("test"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test2() {

		User user = new User();
		user.setId(1);
		user.setUsername("车德庆");
		Contact contact = new Contact();
		contact.setAddress1("宁波海曙区高塘三村23撞70号104室");
		contact.setAddress2("宁波江北区湖景花园1203");
		user.setContact(contact);
		try {
			MemCached cache = MemCached.getInstance();
			cache.add("user", user, new Date(20000));
			user = (User) cache.get("user");
			System.out.println("user:" + user.getId() + ","
					+ user.getUsername());
			System.out.println(user.getContact().getAddress1());
			System.out.println(user.getContact().getAddress2());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test3() {
		try {
			MemCached cache = MemCached.getInstance();
			//cache.add("hello", 234);
			//cache.replace("hello", 234);
			cache.set("hello", 234);//set = add + replace
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test4() {
		try {
			MemCached cache = MemCached.getInstance();
			int i=(Integer)cache.get("hello");
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test5() {
		try {
			MemCached cache = MemCached.getInstance();
			cache.delete("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test6(){
		try {
			MemCached cache = MemCached.getInstance();	
			cache.delete("hello", new Date(60000));//同 test5
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		test1();
		
	}
}
