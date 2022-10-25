package org.example;

public class UserDaoFactory {

    // UserDao 타입의 오브젝트를 만들고 준비시킨다.
    public UserDao awsUserDao(){
        ConnectionMaker connectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }

    public UserDao localUserDao(){
        ConnectionMaker connectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
