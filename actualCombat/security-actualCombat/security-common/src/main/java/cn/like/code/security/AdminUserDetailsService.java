package cn.like.code.security;

import cn.like.code.entity.Admin;
import cn.like.code.entity.Authorities;
import cn.like.code.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * 自定义 UserDetailsService
 *
 * @author like
 * @date 2021/06/03
 */
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Admin admin =
                adminMapper.selectOne(adminMapper.lambdaQuery().eq(Admin::getUsername, username));
        if (!Objects.isNull(admin)) {
            List<Authorities> authorities = adminMapper.getAdminAuthorities(admin.getUsername());
            return new AdminUserDetails(admin,authorities);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}