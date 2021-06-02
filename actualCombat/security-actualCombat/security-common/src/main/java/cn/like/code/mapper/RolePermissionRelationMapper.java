package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.RolePermissionRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户角色和权限关系表(RolePermissionRelation)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:24:02
 */
@Mapper
public interface RolePermissionRelationMapper extends BaseMapper<RolePermissionRelation> {
}