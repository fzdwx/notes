package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户权限表(Permission)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:23:56
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}