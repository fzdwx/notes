package cn.like.code.entity.dto;

import cn.like.code.entity.Client;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author like
 * @date 2021/6/4 13:16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends Client {

    /**
     * 客户端可访问的资源 Id
     */
    private Set<String> resourceIds;

    /**
     * 客户端职权
     */
    private Set<String> authorities;

}