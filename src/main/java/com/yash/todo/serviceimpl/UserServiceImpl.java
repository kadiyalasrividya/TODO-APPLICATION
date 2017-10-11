package com.yash.todo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.yash.todo.dao.UserDao;
import com.yash.todo.daoimpl.UserDaoImpl;
import com.yash.todo.pojo.User;
import com.yash.todo.service.UserService;

@Service
@DependsOn("userDaoImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public UserDao getUserDaoImpl() {
		return userDao;
	}

	public void setUserDAOImpl(UserDaoImpl userDaoImpl) {
		this.userDao = userDaoImpl;
	}

	public void register(User user) {
		userDao.register(user);
	}

	public User validateUser(String loginName, String password) {
		return userDao.validateUser(loginName, password);
	}

}
