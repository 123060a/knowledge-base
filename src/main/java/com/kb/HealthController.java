package com.kb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 健康检查 + 数据库连通性验证。
 *
 * 注意这个类的“构造器注入”写法：
 *   Spring 启动时会自动把 JdbcTemplate 对象“注入”进来，你不用自己 new。
 *   （比在字段上写 @Autowired 更推荐，面试会问两者区别）
 */
@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 验证服务器活着：http://127.0.0.1:8080/ping
     */
    @GetMapping("/ping")
    public String ping() {
        return "hello 知识库";
    }

    /**
     * 验证数据库连得通：http://127.0.0.1:8080/count
     *
     * queryForObject(sql, 返回类型) 干三件事：
     *   1. 从连接池拿一个连接
     *   2. 执行 SQL
     *   3. 把结果转成你指定的类型，然后归还连接
     *
     * 你昨天用 56 行 JDBC 手写的事，现在一行就干完了。
     */
    @GetMapping("/count")
    public Map<String, Object> count() {
        Integer n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM note", Integer.class);

        // 用 LinkedHashMap 保证 JSON 字段顺序稳定（HashMap 无序）
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("noteCount", n);
        return data;
    }
}
