package com.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {
	public static void main(String[] args) throws ParseException {
		int   MyIntArray[ ] = { 10 , 20 , 30 , 40 , 50 , 60 , 70};  
		int  s = 0 ;  
		for (int i=0;i<MyIntArray.length;i++){
			if (i%2==1){
				s+=MyIntArray[i];
			}
		}
		
			 
			     System.out.println( s );  
		
	}
}
