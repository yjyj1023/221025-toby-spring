package org.example;

import user.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {

        //환경 변수 불러오기
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");

        //db 연결을 위한 Connection 가져오기
        Connection c = DriverManager.getConnection(dbHost, dbUser,dbPassword);

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
        //환경 변수 불러오기
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");

        //db 연결을 위한 Connection 가져오기
        Connection c = DriverManager.getConnection(dbHost, dbUser,dbPassword);

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User("3","YeonJae","1234");

        dao.add(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getName());

        System.out.println(user2.getId()+"조회 성공");

    }

}
