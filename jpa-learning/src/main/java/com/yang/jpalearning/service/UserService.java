package com.yang.jpalearning.service;

import com.yang.jpalearning.dao.UserDao;
import com.yang.jpalearning.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User findByUsername(String username){
       return userDao.findByUsername(username);
    }

}
