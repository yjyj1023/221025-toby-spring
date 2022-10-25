package org.example;

import user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {


    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        SimpleConnectionMaker simpleConnectionMaker = new SimpleConnectionMaker();
        UserDao userDao = new UserDao(simpleConnectionMaker);
        User user = new User("8", "EternityHwan","1123");
        userDao.add(user);

        User selectedUser = userDao.get("8");
        Assertions.assertEquals("EternityHwan", selectedUser.getName());
    }
}