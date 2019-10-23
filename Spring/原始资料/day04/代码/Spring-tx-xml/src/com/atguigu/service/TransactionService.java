package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.UserDao;

@Service
public class TransactionService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

	public void multiUpdate() throws Exception {
		userDao.updateUser();
		bookDao.updateBook();
	}

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	public void multiTransaction() {

		bookService.updateBook();

		userService.updateUser();

	}

}
