package cn.like.code.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author like
 * @date 2021/6/3 10:06
 */
@RestController
@RequestMapping("/api")
public class HelloController {


    @RequestMapping("/hello")
    public String hello() {
        return "world";
    }
}
