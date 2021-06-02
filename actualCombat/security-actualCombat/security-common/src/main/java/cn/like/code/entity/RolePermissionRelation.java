package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 后台用户角色和权限关系表(RolePermissionRelation)表实体类
 *
 * @author like
 * @since 2021-06-02 12:23:59
 */
@Data
@TableName("role_permission_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户角色和权限关系表")
@SuppressWarnings("serial")
public class RolePermissionRelation extends BaseEntity<RolePermissionRelation> {

    public static final String id_col_name = "id";
    public static final String roleId_col_name = "role_id";
    public static final String permissionId_col_name = "permission_id";


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * role表主键
     */
    @ApiModelProperty(value = "role表主键")
    private Long roleId;

    /**
     * permission表主键
     */
    @ApiModelProperty(value = "permission表主键")
    private Long permissionId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}