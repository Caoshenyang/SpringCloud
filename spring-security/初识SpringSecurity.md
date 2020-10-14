# Spring Security - 初识Spring Security

创建spring boot工程

添加起始依赖 web、Security

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.yang</groupId>
    <artifactId>spring-security</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-security</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
  </dependencies>
</project>

```

启动类`SpringSecurityApplication`添加`api`

```java
@RestController
@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @GetMapping("/hi")
    public String hi (){
        return "hi,spring-security";
    }
}
```

启动项目

访问`http://localhost:8080/hi`

发现路径自动跳转到`http://localhost:8080/login` 提示要求登录

当引入`Spring Security` 后，没有添加任何的配置或拦截编码，但是`Spring Security`有一个默认的运行状态，

要求经过**HTTP**基本认证后才能访问**URL**资源

**默认用户名** user

**默认动态密码** 控制台打印

```java
Using generated security password: 5f226ca2-5bc6-4e45-9f67-94760c5353bd

```

输入用户名和密码，点击 `Sign in`

页面跳转 `http://localhost:8080/hi`,页面输出 hi,spring-security



我们也可以自定义登录用户名和密码

打开配置文件`application.yml`

添加配置

```yml
spring:
  security:
    user:
      name: caoshenyang
      password: 123456

```

重新启动项目

发现控制台不再打印密码

访问接口``http://localhost:8080/hi``

输入自定义的用户名密码

登录成功



通常情况下一般不会选择这种**HTTP**基本认证的方式，因为安全性差、无法携带cookie，灵活性不足。

基本采用表单认证，自己实现验证逻辑，提高安全性