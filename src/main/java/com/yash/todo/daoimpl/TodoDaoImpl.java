package com.yash.todo.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yash.todo.dao.TodoDao;
import com.yash.todo.pojo.ToDo;

@Repository
public class TodoDaoImpl implements TodoDao {
	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public void insert(ToDo todo) {

		String sql = "insert into todos(name,todoDate,completed,userId) values (?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { todo.getName(), todo.getDate(), todo.isCompleted(), todo.getUserId() });
		System.out.println("Todo Item Successfully saved!!");

	}

	public List<ToDo> getAllTodos(int id) {

		return jdbcTemplate.query("select * from todos where userId=?", new Object[] { id }, new TodoRowMapper());
	}

	private static final class TodoRowMapper implements RowMapper<ToDo> {

		public ToDo mapRow(ResultSet resultSet, int arg1) throws SQLException {
			ToDo todo = new ToDo();
			todo.setId(resultSet.getInt("id"));
			todo.setName(resultSet.getString("name"));
			todo.setDate(resultSet.getDate("todoDate"));
			todo.setCompleted(resultSet.getBoolean("completed"));
			return todo;
		}

	}

	public void delete(int id) {
		String sql = "delete from todos where id=?";
		jdbcTemplate.update(sql, id);

	}

	public void setCompleted(int id) {
		String sql = "update todos set completed=? where id=?";
		jdbcTemplate.update(sql, new Object[] { true, id });

	}

	public void deleteCompleted(int id) {
		String sql = "delete from todos where userId=? and completed=?";
		jdbcTemplate.update(sql, id, true);

	}

	@Override
	public void update(ToDo todo) {
		String sql = "update todos set name=?,todoDate=? where id=?";
		jdbcTemplate.update(sql, new Object[] { todo.getName(), todo.getDate(), todo.getId() });
	}

}
