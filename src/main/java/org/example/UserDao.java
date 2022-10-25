package org.example;

import org.springframework.dao.EmptyResultDataAccessException;
import user.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            //db접속
            c = connectionMaker.makeConnection();

            //sql을 담은 PreparedStatement 만들고 setString으로 값넣기
            ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            //만들어진 PreparedStatement 실행하기
            ps.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
            }
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //db접속
            c = connectionMaker.makeConnection();

            //sql을 담은 PreparedStatement 만들고 setString으로 값넣기
            ps = c.prepareStatement("SELECT id,name,password FROM users WHERE id = ?");
            ps.setString(1, id);

            //조회의 경우 sql쿼리의 실행결과를 ResultSet으로 받아서 정보를 저장할 오브젝트(User)에 옮겨줌.
            rs = ps.executeQuery();

            //User는 null로 초기화
            User user = null;

            //select문의 존재여부 확인(다음 행이 존재하면 true 리턴)
            if(rs.next()){
                user = new User(rs.getString("id"),
                        rs.getString("name"), rs.getString("password"));
            }

            //결과가 없으면 User는 null상태 그대로 이므로 예외를 던져준다.
            if(user == null) throw new EmptyResultDataAccessException(1);
            return user;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
            }
            try {
                ps.close();
            } catch (SQLException e) {
            }
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
            }
            try {
                ps.close();
            } catch (SQLException e) {
            }
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = new DeleteAllStatement().makePreparedStatement(c);

            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }

            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
