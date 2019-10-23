package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateUser() {
		userDao.updateUser();
	}
	
}
