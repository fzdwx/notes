package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ClientAuthority;
import cn.like.code.entity.dto.ClientAuthorityResourceAddressMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Set;

/**
 * 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).(ClientAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:57
 */
@Mapper
public interface ClientAuthorityMapper extends BaseMapper<ClientAuthority> {


    /**
     * Description: 组织客户端职权 - 资源地址的映射关系<br>
     * Details: 资源地址格式为: 端点名@资源 ID
     *
     * @return {@link Set<ClientAuthorityResourceAddressMapping>}
     */
    Set<ClientAuthorityResourceAddressMapping> composeClientAuthorityResourceAddressMapping();
}