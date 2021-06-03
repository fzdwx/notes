package cn.like.code.api;


import cn.hutool.core.util.StrUtil;
import cn.like.code.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试
 *
 * @author like
 * @date 2021/6/3 10:06
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/hello")
    public String hello() {
        return "world";
    }

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = StrUtil.subAfter(header, "bearer ", false);
        return jwtTokenUtil.getUserNameFromToken(token);
    }
}
