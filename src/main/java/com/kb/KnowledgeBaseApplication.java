package com.kb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整个项目的启动入口。
 *
 * @SpringBootApplication 这一个注解干了三件事：
 *   1. @Configuration        —— 这个类是配置类
 *   2. @EnableAutoConfiguration —— 让 Spring Boot 自动装配（看到 web 依赖就起 Tomcat，看到 MySQL 驱动就准备数据源）
 *   3. @ComponentScan        —— 扫描 com.kb 包及其子包下的所有 @Controller / @Service 等
 *
 * 注意第 3 点：所以以后所有业务类都必须放在 com.kb 或它的子包下，
 * 放到别的地方 Spring 是找不到的（这是新手最常见的坑之一）。
 */
@SpringBootApplication
public class KnowledgeBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeBaseApplication.class, args);
    }
}
