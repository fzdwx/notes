package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.MappingAdminAuthorityToResource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户职权和资源的映射表.(MappingAdminAuthorityToResource)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:59
 */
@Mapper
public interface MappingAdminAuthorityToResourceMapper extends BaseMapper<MappingAdminAuthorityToResource> {
}