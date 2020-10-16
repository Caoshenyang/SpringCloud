package com.yang.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.springsecurity.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User findByUserName(@Param("username") String username);
}
