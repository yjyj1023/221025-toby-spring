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
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDaoFactory().awsUserDao();
        User user = new User("8", "EternityHwan","1123");
        userDao.add(user);

        User selectedUser = userDao.get("8");
        Assertions.assertEquals("EternityHwan", selectedUser.getName());
    }
}