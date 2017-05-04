package com.dayuanit.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	
	private static ApplicationContext context;
	
	public static void init() {
		System.out.println("==============SpringUtil================");
		context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-config.xml"});
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	
}
