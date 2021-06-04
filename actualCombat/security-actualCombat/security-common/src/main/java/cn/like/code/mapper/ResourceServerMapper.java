package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ResourceServer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源服务器. 可提供客户端访问的资源服务器定义.(ResourceServer)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:14:10
 */
@Mapper
public interface ResourceServerMapper extends BaseMapper<ResourceServer> {
}