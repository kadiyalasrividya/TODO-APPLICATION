package com.yash.todo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yash.todo.pojo.ToDo;
import com.yash.todo.pojo.User;
import com.yash.todo.service.TodoService;
import com.yash.todo.serviceimpl.TodoServiceImpl;

@Controller
@DependsOn("todoServiceImpl")
@Scope("session")
@RequestMapping("/todoapp")
public class TodoController {

	@Autowired
	private TodoService todoService;

	public TodoService getUserServiceImpl() {
		return todoService;
	}

	public void setUserServiceImpl(TodoServiceImpl todoServiceImpl) {
		this.todoService = todoServiceImpl;
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/";
	}

	@RequestMapping("/todo")
	public String showApp(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);
		return "todo";
	}

	@RequestMapping(value = "/addTodo", method = RequestMethod.POST)
	public String addTodo(@RequestParam String todoName, @RequestParam String todoDate, Model model,
			HttpSession session) {

		ToDo todo = new ToDo();
		todo.setName(todoName);

		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(todoDate);
			java.sql.Date date = new java.sql.Date(date1.getTime());
			System.out.println(date);
			todo.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		todo.setCompleted(false);
		User user = (User) session.getAttribute("loggedInUser");
		todo.setUserId(user.getId());
		todoService.addTodo(todo);

		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);

		return "todo";
	}

	/*
	 * @RequestMapping("/getTodo") public String getTodo(@RequestParam int id,
	 * HttpSession session, Model model) { User user = (User)
	 * session.getAttribute("loggedInUser"); for(Todo todo : Todo.todoList) {
	 * if(todo.getId() == id && todo.getUserId() == user.getId()) { String name
	 * = todo.getName(); String date = todo.getDate().toString();
	 * model.addAttribute("toName",name); model.addAttribute("toDate",date); } }
	 * return "todo"; }
	 */

	@RequestMapping("/removeTodo")
	public String removeTodo(@RequestParam int id, Model model, HttpSession session) {
		todoService.deleteTodo(id);

		User user = (User) session.getAttribute("loggedInUser");

		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);

		return "todo";
	}

	@RequestMapping(value = "editTodo", method = RequestMethod.POST)
	public String editTodo(@RequestParam int id, @RequestParam String name, @RequestParam String date,
			HttpSession session, Model model) {

		ToDo todo = new ToDo();
		todo.setId(id);
		todo.setName(name);

		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			java.sql.Date date2 = new java.sql.Date(date1.getTime());
			System.out.println(date2);
			todo.setDate(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		todoService.editTodo(todo);
		User user = (User) session.getAttribute("loggedInUser");

		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);
		return "todo";
	}

	@RequestMapping("/complete")
	public String complete(@RequestParam int id, Model model, HttpSession session) {
		todoService.markCompleted(id);

		User user = (User) session.getAttribute("loggedInUser");

		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);

		return "todo";
	}

	@RequestMapping("showCompleted")
	public String showCompleted(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		ToDo.todoList = todoService.getAllTodos(user.getId());
		List<ToDo> todos = new ArrayList<>();
		for (ToDo todo : ToDo.todoList) {
			if (todo.isCompleted())
				todos.add(todo);
		}
		model.addAttribute("todos", todos);
		return "todo";
	}

	@RequestMapping("showNotCompleted")
	public String showNotCompleted(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		ToDo.todoList = todoService.getAllTodos(user.getId());
		List<ToDo> todos = new ArrayList<>();
		for (ToDo todo : ToDo.todoList) {
			if (!todo.isCompleted())
				todos.add(todo);
		}
		model.addAttribute("todos", todos);
		return "todo";
	}

	@RequestMapping("removeCompleted")
	public String removeCompleted(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		todoService.removeCompleted(user.getId());

		ToDo.todoList = todoService.getAllTodos(user.getId());
		model.addAttribute("todos", ToDo.todoList);
		return "todo";
	}

}
