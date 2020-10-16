package com.yang.springsecurity.configuration;

import com.yang.springsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/myLogin.html")
                // 指定处理登录请求的路径,修改请求的路径，默认为/login
                .loginProcessingUrl("/mylogin")
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

//    /**
//     * 基于默认数据库数据模型用户设置
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        if (!manager.userExists("aa")) {
//            //MD5 加密 名文 111 加密后 698d51a19d8a121ce581499d7b701668
//            manager.createUser(User.withUsername("aa").password("{MD5}698d51a19d8a121ce581499d7b701668").roles("USER").build());
//
//        }
//        if (!manager.userExists("bb")) {
//            manager.createUser(User.withUsername("bb").password("{noop}222").roles("USER").build());
//        }
//        return manager;
//    }


//    /**
//     * 基于内存多用户配置
//     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        //MD5 加密 名文 111 加密后 698d51a19d8a121ce581499d7b701668
//        manager.createUser(User.withUsername("aa").password("{MD5}698d51a19d8a121ce581499d7b701668").roles("USER").build());
//        manager.createUser(User.withUsername("bb").password("{noop}222").roles("USER").build());
//
//        return manager;
//    }
//
//    /**
//     * 基于内存多用户配置
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("tom").password("111").roles("ADMIN","USER")
//                .and()
//                .withUser("lisi").password("222").roles("USER");
//    }

}
