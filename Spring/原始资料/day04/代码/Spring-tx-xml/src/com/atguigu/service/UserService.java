package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void updateUser() {
		userDao.updateUser();
	}

}
