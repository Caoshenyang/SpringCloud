package com.yang.jpalearning.dao;

import com.yang.jpalearning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 继承 JpaRepository 可实现对数据库读写操作
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
