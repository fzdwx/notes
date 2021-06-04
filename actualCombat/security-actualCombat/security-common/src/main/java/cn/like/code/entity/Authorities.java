package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户权限(Authorities)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:15
 */
@Data
@TableName("authorities")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户权限")
@SuppressWarnings("serial")
public class Authorities extends BaseEntity<Authorities> {

    public static final String username_col_name = "username";
    public static final String authority_col_name = "authority";


    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String username;

    /**
     * 权限名称 ROLE_ADMIN
     */
    @ApiModelProperty(value = "权限名称 ROLE_ADMIN")
    private String authority;


}