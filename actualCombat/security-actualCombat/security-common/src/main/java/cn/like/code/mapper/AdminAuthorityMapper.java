package cn.like.code.mapper;

import cn.like.code.base.BaseMapper;
import cn.like.code.entity.AdminAuthority;
import cn.like.code.entity.dto.AdminAuthorityResourceAddressMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).(AdminAuthority)表数据库访问层
 *
 * @author like
 * @since 2021-06-04 13:13:52
 */
@Mapper
public interface AdminAuthorityMapper extends BaseMapper<AdminAuthority> {

    /**
     * Description: 组织用户端职权 - 资源地址的映射关系<br>
     * Details: 资源地址格式为: 端点名@资源 ID
     *
     * @return {@link Set< AdminAuthorityResourceAddressMapping >}
     */
    Set<AdminAuthorityResourceAddressMapping> composeUserAuthorityResourceAddressMapping();
}