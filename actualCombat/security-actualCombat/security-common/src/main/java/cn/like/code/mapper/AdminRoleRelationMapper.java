package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.AdminRoleRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户和角色关系表(AdminRoleRelation)表数据库访问层
 *
 * @author like
 * @since 2021-06-02 12:23:54
 */
@Mapper
public interface AdminRoleRelationMapper extends BaseMapper<AdminRoleRelation> {
}