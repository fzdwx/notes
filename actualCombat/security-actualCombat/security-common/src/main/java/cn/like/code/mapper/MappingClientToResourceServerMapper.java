package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.MappingClientToResourceServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.(MappingClientToResourceServer)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:14:06
 */
@Mapper
public interface MappingClientToResourceServerMapper extends BaseMapper<MappingClientToResourceServer> {

    /**
     * Description: 查询匹配的资源服务器 ID
     *
     * @param id 客户端 ID
     * @return java.util.Set<java.lang.String>
     * @author LiKe
     * @date 2020-06-17 11:39:08
     */
    Set<String> queryResourceServerIds(String id);

}