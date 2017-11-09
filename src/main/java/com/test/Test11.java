package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test11 {

	public Test11() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Work w1 = new Work();
		w1.setA(1);
		w1.setB(1);
		Work w2 = new Work();
		w2.setA(1);
		List<Work>  list =new ArrayList();
		list.add(w1);
		
		list.add(w2);
		
		for (Work s:list){
			System.out.println(s.getA());
			
		}
		
		

	}

}
