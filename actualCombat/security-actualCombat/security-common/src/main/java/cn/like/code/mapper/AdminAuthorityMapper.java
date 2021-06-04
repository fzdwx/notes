package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.AdminAuthority;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).(AdminAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:52
 */
@Mapper
public interface AdminAuthorityMapper extends BaseMapper<AdminAuthority> {
}