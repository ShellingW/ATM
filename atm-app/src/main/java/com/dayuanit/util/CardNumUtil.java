package com.dayuanit.util;

import java.util.Random;

public class CardNumUtil {
	
	private static final String PRE_CARD_NUM = "622";
	
	
	
	public static String create(){
		Random random = new Random(System.currentTimeMillis());
		String tmpNum = "";
		for(int i=0;i<12;i++){
			tmpNum += String.valueOf(random.nextInt(10));
		}
			
		return PRE_CARD_NUM + tmpNum;
		
	}
	

	
}
