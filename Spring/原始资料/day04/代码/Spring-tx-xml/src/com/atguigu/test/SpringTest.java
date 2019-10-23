package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.service.TransactionService;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		TransactionService transactionService = (TransactionService) applicationContext.getBean("transactionService");
		transactionService.multiUpdate();
	}
	
	@Test
	public void testMultiTransaction() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		TransactionService transactionService = (TransactionService) applicationContext.getBean("transactionService");
		transactionService.multiTransaction();
	}

}
