package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.AdminPermissionRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)(AdminPermissionRelation)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:23:51
 */
@Mapper
public interface AdminPermissionRelationMapper extends BaseMapper<AdminPermissionRelation> {
}