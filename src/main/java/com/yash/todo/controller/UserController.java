package com.yash.todo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yash.todo.pojo.User;
import com.yash.todo.service.UserService;
import com.yash.todo.serviceimpl.UserServiceImpl;

@Controller
@DependsOn("userServiceImpl")
@Scope("session")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	public UserService getUserServiceImpl() {
		return userService;
	}

	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}

	@RequestMapping("/")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/register")
	public String registerPage() {
		return "register";
	}

	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public String regUser(@ModelAttribute User user) {
		userService.register(user);
		return "login";
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String authenticateUser(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password, Model model, HttpServletRequest request) {

		User user = userService.validateUser(loginName, password);

		if (user == null) {
			model.addAttribute("msg", "Username or password incorrect!!");
			return "login";
		}
		request.getSession().setAttribute("loggedInUser", user);
		return "redirect:/todoapp/todo";
	}
}
