package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户角色表(Role)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:23:59
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}