package com.yash.todo.dao;

import com.yash.todo.pojo.User;

public interface UserDao {
	void register(User user);

	User validateUser(String loginName, String password);
}
