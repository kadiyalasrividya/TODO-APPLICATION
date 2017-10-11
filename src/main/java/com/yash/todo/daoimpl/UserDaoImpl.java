package com.yash.todo.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yash.todo.dao.UserDao;
import com.yash.todo.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private DataSource datasource;

	private JdbcTemplate jdbcTemplate;

	public DataSource getDatasource() {
		return datasource;
	}

	@Autowired
	public void setDatasource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(this.datasource);
	}

	public void register(User user) {
		String sql = "insert into users(name,contact,username,password) values(?,?,?,?)";
		jdbcTemplate.update(sql,
				new Object[] { user.getName(), user.getContact(), user.getUsername(), user.getPassword() });
	}

	public User validateUser(String loginName, String password) {
		return jdbcTemplate.queryForObject("select * from users where username=? and password=?",
				new Object[] { loginName, password }, new UserMapper());
	}

	class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setContact(rs.getString("contact"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	}
}
