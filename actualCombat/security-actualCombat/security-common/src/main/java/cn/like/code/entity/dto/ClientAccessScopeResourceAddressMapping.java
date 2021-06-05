package cn.like.code.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端访问范围到资源路径的映射对象
 * @author: like
 * @since: 2021/6/5 9:30
 * @email: 980650920@qq.com
 */
@Data
@NoArgsConstructor
public class ClientAccessScopeResourceAddressMapping {

    public static final String CACHE_SUFFIX = "client-access-scope";

    /**
     * 客户端访问范围名称
     */
    private String clientAccessScopeName;

    /**
     * 资源路径
     */
    private String resourceAddress;
}
