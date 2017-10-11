package com.yash.todo.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDo {

	private int id;
	private String name;
	private Date date;
	private int userId;
	private boolean completed;

	public static List<ToDo> todoList = new ArrayList<ToDo>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}