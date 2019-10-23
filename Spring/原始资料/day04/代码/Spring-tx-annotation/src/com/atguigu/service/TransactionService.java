package com.atguigu.service;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.UserDao;

@Service
public class TransactionService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

	/**
	 * 表示此方法支持事务<br/>
	 * 默认情况下是RunTimeException运行时和它的子异常。会回滚事务<br/>
	 * timeout=3 表示3秒后不允许再执行sql语句。
	 */
	@Transactional(timeout = 3)
	public void multiUpdate() throws Exception {
		userDao.updateUser();
		Thread.sleep(4000);
		bookDao.updateBook();
	}

	
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void multiTransaction() {
		
		bookService.updateBook();
		
		userService.updateUser();
		
	}
	
	
	
	
	
	
	
	
}
