package cn.like.code.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户职权到资源地址的映射对象
 *
 * @author: like
 * @since: 2021/6/5 10:01
 * @email: 980650920@qq.com
 * @desc:
 */
@Data
@NoArgsConstructor
public class AdminAuthorityResourceAddressMapping {

    /**
     * 缓存前缀
     */
    public static final String CACHE_SUFFIX = "admin-authority";

    /**
     * 用户职权名
     */
    private String adminAuthorityName;

    /**
     * 资源地址
     */
    private String resourceAddress;
}
