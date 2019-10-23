package com.atguigu.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.pojo.Calculate;
import com.atguigu.pojo.Calculator;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Calculate calculate = (Calculate) applicationContext.getBean("calculator");
		
		calculate.add(100, 100);
		System.out.println("==========================");
		calculate.div(100, 0);
	}
	
}
