package com.yash.todo.service;

import java.util.List;

import com.yash.todo.pojo.ToDo;

public interface TodoService {

	public void addTodo(ToDo todo);

	public List<ToDo> getAllTodos(int id);

	public void deleteTodo(int id);

	public void markCompleted(int id);

	public void removeCompleted(int id);

	public void editTodo(ToDo todo);

}
