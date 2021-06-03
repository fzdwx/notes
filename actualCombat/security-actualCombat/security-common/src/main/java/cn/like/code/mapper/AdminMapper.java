package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Admin;
import cn.like.code.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户表(Admin)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:23:49
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获得管理员权限集合
     *
     * @param id adminId
     * @return {@link List<Permission>}
     */
    List<Permission> getAdminPermissions(@Param("id") Long id);
}