package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.dao.BookDao;

@Service
public class BookService {

	@Autowired
	private BookDao bookDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBook() {
		bookDao.updateBook();
	}

}
