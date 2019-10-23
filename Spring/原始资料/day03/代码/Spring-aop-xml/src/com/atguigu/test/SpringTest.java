package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.pojo.Calculate;

@ContextConfiguration(locations="classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {

	@Autowired
	Calculate calculate;
	
	@Test
	public void test1() throws Exception {
		calculate.add(100, 100);
		System.out.println("==========================");
		calculate.div(100, 0);
	}
	
//	@Test
//	public void test1() throws Exception {
//		
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		
//		Calculate calculate = (Calculate) applicationContext.getBean("calculator");
//		
//		calculate.add(100, 100);
//		System.out.println("==========================");
//		calculate.div(100, 0);
//	}
	
}
