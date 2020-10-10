package com.yang.jpalearning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Entity 表明该类是一个实体类，和数据库表明相对应
 * @Id     表明该字段对应数据库中的id
 * @GeneratedValue 配置id字段为自增长
 * @Column 对应数据库中的字段
 */
@Data
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column
    private String password;


}
