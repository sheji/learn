package com.atguigu.dao;

import org.springframework.stereotype.Repository;

import com.atguigu.pojo.User;

@Repository
public class UserDao extends BaseDao<User> {

	@Override
	public void save(User entity) {
		System.out.println("UserDao 保存 -> " + entity);
	}

}
