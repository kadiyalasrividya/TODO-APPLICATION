package com.yash.todo.dao;

import java.util.List;

import com.yash.todo.pojo.ToDo;

public interface TodoDao {

	public void insert(ToDo todo);

	public List<ToDo> getAllTodos(int id);

	public void delete(int id);

	public void setCompleted(int id);

	public void deleteCompleted(int id);

	public void update(ToDo todo);

}
