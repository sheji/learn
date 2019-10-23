package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	}
	
	@Test
	public void test2() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(applicationContext.getBean("p19"));
	}
	
	@Test
	public void test3() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("p20") );
		System.out.println( applicationContext.getBean("p20") );
		System.out.println( applicationContext.getBean("p20") );
		System.out.println( applicationContext.getBean("p20") );
		
	}
	
	@Test
	public void test4() throws Exception {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("p21") );
		applicationContext.close();
	}
	
}
