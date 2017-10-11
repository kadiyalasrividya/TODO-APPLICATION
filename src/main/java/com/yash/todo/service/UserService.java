package com.yash.todo.service;

import com.yash.todo.pojo.User;

public interface UserService {
	void register(User user);

	public User validateUser(String loginName, String password);

}
