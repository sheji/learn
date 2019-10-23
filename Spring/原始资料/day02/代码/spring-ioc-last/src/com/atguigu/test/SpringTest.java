package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.User;
import com.atguigu.service.BookService;
import com.atguigu.service.UserService;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		BookService bookService = (BookService) applicationContext.getBean("bookService");
		bookService.save(new Book());
		System.out.println("===========================================");
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.save(new User());
		
	}
	
}
