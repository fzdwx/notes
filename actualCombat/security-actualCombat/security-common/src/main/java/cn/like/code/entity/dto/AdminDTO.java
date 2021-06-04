package cn.like.code.entity.dto;

import cn.like.code.entity.Admin;
import cn.like.code.entity.AdminAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AdminDTO extends Admin {

    /**
     * 当前用户对应的职权 {@link AdminAuthority}
     */
    private Set<String> authorities;
}