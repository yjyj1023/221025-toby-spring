package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDaoFactory().awsUserDao();

        //컬럼삭제
        userDao.deleteAll();

        //유저 추가
        User user = new User("1", "EternityHwan","1123");
        userDao.add(user);

        //users 레코드의 개수
        assertEquals(1,userDao.getCount());

        //id = 1인 유저의 이름과 패스워드 불러오기
        User selectedUser = userDao.get("1");
        assertEquals("EternityHwan", selectedUser.getName());
        assertEquals("1123", selectedUser.getPassword());
    }
}