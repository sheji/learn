package com.atguigu.test;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataSourceTest {

	@Test
	public void test1() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
		
		System.out.println( dataSource.getConnection() );
		
	}
	
}
