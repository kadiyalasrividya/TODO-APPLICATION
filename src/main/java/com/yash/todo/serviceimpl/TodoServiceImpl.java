package com.yash.todo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.yash.todo.dao.TodoDao;
import com.yash.todo.daoimpl.TodoDaoImpl;
import com.yash.todo.pojo.ToDo;
import com.yash.todo.service.TodoService;

@Service
@DependsOn("todoDaoImpl")
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoDao todoDao;

	public TodoDao getUserDaoImpl() {
		return todoDao;
	}

	public void setUserDAOImpl(TodoDaoImpl todoDaoImpl) {
		this.todoDao = todoDaoImpl;
	}

	public void addTodo(ToDo todo) {
		// TODO Auto-generated method stub
		todoDao.insert(todo);
	}

	public List<ToDo> getAllTodos(int id) {
		// TODO Auto-generated method stub
		return todoDao.getAllTodos(id);
	}

	public void deleteTodo(int id) {
		// TODO Auto-generated method stub
		todoDao.delete(id);
	}

	public void markCompleted(int id) {
		// TODO Auto-generated method stub
		todoDao.setCompleted(id);
	}

	public void removeCompleted(int id) {
		// TODO Auto-generated method stub
		todoDao.deleteCompleted(id);
	}

	@Override
	public void editTodo(ToDo todo) {
		todoDao.update(todo);
	}

}
