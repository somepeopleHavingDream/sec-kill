package org.yangxin.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yangxin.seckill.dao.UserDao;
import org.yangxin.seckill.domain.User;

/**
 * @author yangxin
 * 2022/06/04 01:32
 */
@Service
public class UserService {
	
	private final UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getById(int id) {
		 return userDao.getById(id);
	}
}
