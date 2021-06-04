package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Admin;
import cn.like.code.entity.Authorities;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户表(Admin)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 09:09:13
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取用户的权限
     *
     * @param username 用户名
     * @return {@link List<Authorities>}
     */
    List<Authorities> getAdminAuthorities(@Param("username") String username);
}