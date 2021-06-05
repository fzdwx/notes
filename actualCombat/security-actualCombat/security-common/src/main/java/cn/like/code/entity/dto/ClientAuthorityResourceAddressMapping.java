package cn.like.code.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 客户端职权到资源地址的映射 DTO<br>
 * Details: 资源地址格式: {@code {endpoint}@{resource-server-id}}
 *
 * @author: like
 * @since: 2021/6/5 9:53
 * @email: 980650920@qq.com
 * @desc:
 */
@Data
@NoArgsConstructor
public class ClientAuthorityResourceAddressMapping {

    /**
     * 缓存前缀
     */
    public static final String CACHE_SUFFIX = "client-authority";

    /**
     * 客户端职权名称
     */
    private String clientAuthorityName;

    /**
     * 资源地址
     */
    private String resourceAddress;
}
