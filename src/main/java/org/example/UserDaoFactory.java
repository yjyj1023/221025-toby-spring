package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UserDaoFactory {
    // UserDao 타입의 오브젝트를 만들고 준비시킨다.
    @Bean
    public UserDao awsUserDao(){
        ConnectionMaker connectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
    @Bean
    public UserDao localUserDao(){
        ConnectionMaker connectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
