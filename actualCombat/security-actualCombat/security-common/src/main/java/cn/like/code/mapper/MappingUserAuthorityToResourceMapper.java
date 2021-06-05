package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.MappingUserAuthorityToResource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户职权和资源的映射表.(MappingUserAuthorityToResource)表数据库访问层
 *
 * @author pig4cloud
 * @since 2021-06-05 10:05:04
 */
@Mapper
public interface MappingUserAuthorityToResourceMapper extends BaseMapper<MappingUserAuthorityToResource> {
}