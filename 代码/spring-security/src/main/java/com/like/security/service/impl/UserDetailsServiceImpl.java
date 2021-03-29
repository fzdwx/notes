package com.like.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.security.dao.UserDao;
import com.like.security.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-28 16:50
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.调用userDao 根据用户名查询
        Users users = userDao.selectOne(new QueryWrapper<Users>().eq("username", username));

        // 2.判断
        if (users == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 3.通过校验
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), roles);
    }
}
