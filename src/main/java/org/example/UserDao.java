package org.example;

import user.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        //sql을 담은 PreparedStatement 만들고 setString으로 값넣기
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?)");
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        //만들어진 PreparedStatement 실행하기
        ps.executeUpdate();

        //리소스 닫기
        ps.close();
        c.close();
        System.out.println("DB연동 성공");
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        //sql을 담은 PreparedStatement 만들고 setString으로 값넣기
        PreparedStatement ps = c.prepareStatement("SELECT id,name,password FROM users WHERE id = ?");
        ps.setString(1,id);

        //조회의 경우 sql쿼리의 실행결과를 ResultSet으로 받아서 정보를 저장할 오브젝트(User)에 옮겨쥼.
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"),
                rs.getString("name"), rs.getString("password"));

        //리소스 닫기
        rs.close();
        ps.close();
        c.close();
        return user;
    }

}
