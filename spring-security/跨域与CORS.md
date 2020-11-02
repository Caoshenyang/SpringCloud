# Spring Security - 跨域与CORS



## 一、认识跨域

**跨域是一种浏览器同源安全策略，即浏览器单方面限制脚本的跨域访问。**

- 怎样会造成跨域？

  当前页面**URL**和请求的URL首部不同则会造成跨域。通俗点讲就是两个**URL**地址，端口之前部分，只要有一点不同即发生跨域。

  - 在`http://a.baidu.com`下访问`https://a.baidu.com`资源会形成**协议跨域**。 

  - 在`a.baidu.com`下访问`b.baidu.com`资源会形成**主机跨域**。 

  - 在`a.baidu.com:80`下访问`a.baidu.com:8080`资源会形成**端口跨域**。 

## 二、解决跨域的常见方式

### 1. JSONP

由于浏览器允许一些带有src属性的标签跨域，常见的有**iframe**、**script**、**img**等，所以**JSONP**利用**script**标签可以实习跨域。

**实现思路**

- 前端通过**script**标签请求，并在**callback**中指定返回的包装实体名称为jsonp(可以自定义)

```javascript
<script src="http://aaa.com/getusers?callback=jsonp"></script>
```

- 后端将返回结果包装成所需数据格式

```json
jsonp({
    "error":200,
    "message":"请求成功",
    "data":[{
        "username":"张三",
        "age":20
    }]
})
```

**总结：JSONP实现起来很简单，但是只支持GET请求跨域，存在较大的局限性**



### 2.CORS

**CORS（Cross-Origin Resource Sharing）的规范中有一组新增的HTTP首部字段，允许服务器声明其提供的资源允许哪些站点跨域使用。**

**注意：CORS不支持IE8以下版本的浏览器。**

大多数浏览器中即便是跨域请求，请求依然是会正常发送到服务端，服务端接收并处理，只是返回到浏览器的信息被浏览器拦截屏蔽了。这样会对服务器造成不必要的资源浪费。

在**CORS**的规范中则避免了这个问题：

- 浏览器在请求时会先发一个请求方法为**OPTIONS**的预检请求，用于确认是否允许跨域，只有服务端允许，才会发出实际请求。

- 预检请求允许服务端通知浏览器跨域携带身份凭证（如**cookie**）

**CORS**新增的**HTTP**首部字段由服务器控制，下面介绍几个常用首部字段：

- **Access-Control-Allow-Origin**

  - 设置允许哪些站点跨域请求，使用URL首部匹配原则。
  - 设置为*****表示允许所有网站请求

  **注意：**

  - 当需要浏览器请求携带凭证的时候，不允许设置为*****
  - 设置了具体站点信息，**Vary**需要携带**Origin**属性，因为服务器对不同的域会返回不同的内容：

  ```java
  Access-Control-Allow-Origin: http://aaa.com
  Vary: Accept-Encoding,Origin
  ```

- **Access-Control-Allow-Methods**

  - 仅在预检请求的响应中指定有效
  - 表明服务器允许请求的**HTTP**方法
  - 多个用逗号隔开

- **Access-Control-Allow-Headers**
  - 仅在预检请求的响应中指定有效
  - 表明服务器允许携带的首部字段
  - 多个用逗号隔开

- **Access-Control-Max-Age **
  - 指明本次预检请求的有效期
  - 有效期内无需再次发起请求
- **Access-Control-Allow-Credentials**
  - 为**true**时，通知浏览器接下来的正式请求带上用户凭证信息（**cookie**等），服务器也可以使用**Set-Cookie**向用户浏览器写入新的**cookie**。
  - 此时**Access-Control-Allow-Origin**不能设置为*****



**总结：在使用CORS时，通常有以下两种访问控制场景**

1. 简单请求

   1. 不携带自定义请求头信息的**GET**请求、**HEAD**请求
   2. **Content-Type**为**application/x-www-form-urlencoded**、**multipart/form-data**或 **text/plain**的**POST**请求

   请求时，会在请求头中自动添加一个**Origin**属性，值为当前页面**URL**首部。服务端接收到请求，返回信息。如果返回信息中存在跨域访问控制属性，浏览器会根据这些属性值判断是否被允许，如果允许，则跨域成功。

   ​	所以只需要后端在返回的响应头中添加 **Access-Control-Allow-Origin** 字段并填入允许跨域访问的站点即可。 

2. 预检请求(非简单请求)

   预检请求不同于简单请求，它会发送一个 **OPTIONS** 请求到目标站点，以查明该请求是否安全，防止请求对目标站点的数据造成破坏。

## 三、Spring Security启用CORS支持

**Spring Security对CORS提供了非常好的支持，只需在配置器中启用CORS支持，并编写一 个CORS配置源即可。**

```java
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
        .loginPage("/myLogin.html")
        // 指定处理登录请求的路径,修改请求的路径，默认为/login
        .loginProcessingUrl("/mylogin").permitAll()
        .csrf().disable()
        .cors();
}

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
```

**注意：**

CorsConfigurationSource为这个包下的

import org.springframework.web.cors.CorsConfigurationSource;





