package com.kb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口 —— 只用来验证“服务器活着、能响应 HTTP 请求”。
 *
 * 这不是业务代码，写第一个真接口（新建笔记）时会看到真正的 Controller 长什么样。
 *
 * @RestController = @Controller + @ResponseBody，表示这个类里方法的返回值直接转成 JSON。
 */
@RestController
public class HealthController {

    /**
     * 浏览器访问 http://localhost:8080/ping 会看到 ok
     */
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
