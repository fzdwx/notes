package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.MappingClientToClientAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 客户端到客户端职权的映射表.(MappingClientToClientAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:14:04
 */
@Mapper
public interface MappingClientToClientAuthorityMapper extends BaseMapper<MappingClientToClientAuthority> {

    /**
     * 查询客户端的全选
     *
     * @param clientId 客户机id
     * @return {@link Set<String>}
     */
    Set<String> queryClientAuthorities(String clientId);
}