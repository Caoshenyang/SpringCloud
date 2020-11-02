package com.yang.springsecurity.configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yang.springsecurity.filter.VerificationCodeFilter;
import com.yang.springsecurity.handler.MyAuthenticationFailureHandler;
import com.yang.springsecurity.provider.MyPasswordEncoder;
import com.yang.springsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetailsSource;
    @Autowired
    private AuthenticationProvider myAuthenticationProvider;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加AuthenticationProvider
        auth.userDetailsService(myUserDetailsService).passwordEncoder(myPasswordEncoder);
        //应用MyAuthenticationProvider
//        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                //AuthenticationDetailsSource
//                .authenticationDetailsSource(myWebAuthenticationDetailsSource)
                .loginPage("/myLogin.html")
                // 指定处理登录请求的路径,修改请求的路径，默认为/login
                .loginProcessingUrl("/mylogin").permitAll()
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                //增加自动登录功能，默认为散列加密
                .rememberMe()
                .userDetailsService(myUserDetailsService)
                .tokenRepository(jdbcTokenRepository)
//                .key("autologin")
                .and()
                .sessionManagement()
                //设置最大会话数为1
                .maximumSessions(1)
                //阻止新会话登录，默认为false
                //.maxSessionsPreventsLogin(true)
                //.sessionRegistry(redisSessionRegistry)
                .and()
                .invalidSessionUrl("/")
                .and()
                .csrf().disable()
                .cors();
//        将过滤器添加在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.logout()
//            //指定接受注销请求的路径
//            .logoutUrl("/logout")
//            //注销成功，重定向到该路径下
//            .logoutSuccessUrl("/")
//            //注册成功之后处理方式
//            .logoutSuccessHandler(new LogoutSuccessHandler() {
//                @Override
//                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//
//                }
//            })
//            //使该用户的HttpSession失效
//            .invalidateHttpSession(true)
//                //注销成功，删除指定cookie
//            .deleteCookies("remember-me")
//                //用于注销的处理方式，允许自定义一些清理策略
//            .addLogoutHandler(new LogoutHandler() {
//                @Override
//                public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//
//                }
//            });
    }

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher(){
//        return new HttpSessionEventPublisher();
//    }
//    @Bean
//    SpringSessionBackedSessionRegistry sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry(sessionRepository);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许从百度站点跨域
        corsConfiguration.setAllowedOrigins(Arrays.asList("https://www.baidu.com"));
        //允许GET和POST方法
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST"));
        //允许携带凭证
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //对所有URL生效
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
   }

    @Bean
    public Producer kaptcha() {
        //配置图形验证码的基本参数
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        //图片长度
        properties.setProperty("kaptcha.image.height", "50");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        //使用默认的图形验证码实现，也可以自定义
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
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
