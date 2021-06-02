package cn.like.code.security;

import cn.like.code.entity.Admin;
import cn.like.code.entity.Permission;
import cn.like.code.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminUserService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Admin admin =
                adminMapper.selectOne(adminMapper.lambdaQuery().eq(Admin::getUsername, username));
        if (!Objects.isNull(admin)) {
            List<Permission> permissions = adminMapper.getAdminPermissions(admin.getId());
            return new AdminUserDetails(admin,permissions);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}