package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ClientAuthority;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).(ClientAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:57
 */
@Mapper
public interface ClientAuthorityMapper extends BaseMapper<ClientAuthority> {
}