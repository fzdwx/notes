package cn.like.code.security;

import cn.like.code.entity.Admin;
import cn.like.code.entity.Permission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 */
@Getter
@Setter
public class AdminUserDetails implements UserDetails {

    /** 用户信息 */
    private Admin admin;
    /** 权限列表 */
    private Set<Permission> permissions;
    /** 用户唯一标识 */
    private String token;

    /** 登录时间 */
    private Long loginTime;

    /** 过期时间 */
    private Long expireTime;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    public AdminUserDetails(Admin admin, List<Permission> permissions) {
        this.admin = admin;
        this.permissions = new HashSet<>(permissions);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissions.stream()
                          .filter(permission -> permission.getValue() != null)
                          .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                          .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }
}