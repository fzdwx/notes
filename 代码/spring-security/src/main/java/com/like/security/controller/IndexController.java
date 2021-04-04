package com.like.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("noauth")
    public String noauth() {
        return "no auth controller!";
    }

    @GetMapping("adminOnly")
    public String adminOnly() {
        return "adminOnly controller!";
    }

    @GetMapping("adminAndRole")
    public String adminAndRole() {
        return "adminAndRole controller!";
    }

    @GetMapping("producer")
    public String producer() {
        return "producer controller!";
    }

    @GetMapping("gamer")
    public String gamer() {
        return "gamer controller!";
    }

    @Secured({
                     "ROLE_SALE", "ROLE_MANAGER"
             })
    @GetMapping("update")
    public String update() {
        return "hello update";
    }

    @GetMapping("preAuthorize")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String preAuthorize() {
        return " this preAuthorize";
    }
}
