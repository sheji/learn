package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.User;
import com.atguigu.service.BookService;
import com.atguigu.service.UserService;

/**
 * Spring扩展了Junit测试。测试的上下文环境中自带Spring容器。<br/>
 * 我们要获取Spring容器中的bean对象。就跟写一个属性一样方便。
 */
// @ContextConfiguration配置Spring容器
@ContextConfiguration(locations="classpath:applicationContext.xml")
// @RunWith配置使用Spring扩展之后的Junit测试运行器
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJunitTest {

	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Test
	public void test1() throws Exception {
		bookService.save(new Book());
		System.out.println("===========================================");
		userService.save(new User());
	}
}
