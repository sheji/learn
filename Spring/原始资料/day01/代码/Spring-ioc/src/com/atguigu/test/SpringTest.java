package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p8") );
	}
	
	@Test
	public void test2() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p9") );
		System.out.println( applicationContext.getBean("car") );
		System.out.println( applicationContext.getBean("car02") );
	}
	
	@Test
	public void test3() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p10") );
		
	}
	
	@Test
	public void test4() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("p11") );
	}
	
	@Test
	public void test5() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("p12") );
	}
	
	@Test
	public void test6() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("pset") );
	}
	
	@Test
	public void test7() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p13") );
		System.out.println( applicationContext.getBean("list01") );
		
	}
	
	@Test
	public void test8() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println( applicationContext.getBean("p14") );
		System.out.println( applicationContext.getBean("car") );
	}
	
	@Test
	public void test9() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p15") );
	}
	
	
	@Test
	public void test10() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p16") );
		
	}
	
	@Test
	public void test11() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("p17") );
		
	}
	
	@Test
	public void test12() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println( applicationContext.getBean("parent") );
		System.out.println( applicationContext.getBean("p18") );
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
