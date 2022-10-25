package org.example;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserDao {
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {
        //jdbcTemplate.update()의 경우 두번째부터 파라메터 개수 만큼 ?자리에 값을 넘길 수 있다.
        this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?);",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() throws SQLException{
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        //queryForObject에 두번째 파라메터로 Integer.class를 넘겨줌으로써 int형의 데이터를 받아온다.
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }

    public List<User> getAll() {
        String sql = "select * from users order by id";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.query(sql, rowMapper);
    }

}
