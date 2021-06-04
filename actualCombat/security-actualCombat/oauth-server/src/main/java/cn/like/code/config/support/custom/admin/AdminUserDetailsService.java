package cn.like.code.config.support.custom.admin;

import cn.like.code.mapper.AdminMapper;
import cn.like.code.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * 自定义 UserDetailsService
 *
 * @author like
 * @date 2021/06/03
 */

@Slf4j
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;

    /**
     * 从数据库中根据username 取出 用户对应的权限信息
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名没有发现异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("About to produce UserDetails with user-name: {}", username);

        return new AdminUserDetails(
                Optional.ofNullable(adminService.getAdmin(username))
                        .orElseThrow(()->new UsernameNotFoundException(String.format("用户名未注册 %s", username)))
        );
    }
}