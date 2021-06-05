package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.ClientAccessScope;
import cn.like.code.entity.dto.ClientAccessScopeResourceAddressMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * (ClientAccessScope)表数据库访问层
 *
 * @author pig4cloud
 * @since 2021-06-05 09:23:36
 */
@Mapper
public interface ClientAccessScopeMapper extends BaseMapper<ClientAccessScope> {

    /**
     * desc: 组织客户端访问范围 - 资源地址的映射关系<br>
     * 资源地址格式为: 端点名@资源 ID
     * @return {@link Set<ClientAccessScopeResourceAddressMapping>}
     */
    Set<ClientAccessScopeResourceAddressMapping> composeClientAccessScopeResourceAddressMapping();

}