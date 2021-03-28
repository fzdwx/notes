package com.like.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-28 15:03
 */
@RestController
public class IndexController {

    @GetMapping("hello")
    public String hello() {
        return "welcome to me web!";
    }
}
