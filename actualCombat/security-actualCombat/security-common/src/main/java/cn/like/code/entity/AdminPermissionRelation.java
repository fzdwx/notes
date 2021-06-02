package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)(AdminPermissionRelation)表实体类
 *
 * @author like
 * @since 2021-06-02 12:23:49
 */
@Data
@TableName("admin_permission_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
@SuppressWarnings("serial")
public class AdminPermissionRelation extends BaseEntity<AdminPermissionRelation> {

    public static final String id_col_name = "id";
    public static final String adminId_col_name = "admin_id";
    public static final String permissionId_col_name = "permission_id";
    public static final String type_col_name = "type";


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * admin表主键
     */
    @ApiModelProperty(value = "admin表主键")
    private Long adminId;

    /**
     * permission表主机
     */
    @ApiModelProperty(value = "permission表主机")
    private Long permissionId;
    @ApiModelProperty(value = "")
    private Integer type;

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