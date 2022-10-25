package org.example;

import user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalConnection extends UserDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //환경 변수 불러오기
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");

        //db 연결을 위한 Connection 가져오기
        Connection c = DriverManager.getConnection(dbHost, dbUser,dbPassword);
        return c;
    }
}
