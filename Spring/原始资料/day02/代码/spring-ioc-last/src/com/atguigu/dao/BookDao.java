package com.atguigu.dao;

import org.springframework.stereotype.Repository;

import com.atguigu.pojo.Book;

@Repository
public class BookDao extends BaseDao<Book> {

	@Override
	public void save(Book entity) {
		System.out.println("BookDao 保存 -> " + entity);
	}

}
