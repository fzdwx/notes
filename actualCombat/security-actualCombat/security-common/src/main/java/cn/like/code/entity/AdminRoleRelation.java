package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 后台用户和角色关系表(AdminRoleRelation)表实体类
 *
 * @author like
 * @since 2021-06-02 12:23:52
 */
@Data
@TableName("admin_role_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户和角色关系表")
@SuppressWarnings("serial")
public class AdminRoleRelation extends BaseEntity<AdminRoleRelation> {

    public static final String id_col_name = "id";
    public static final String adminId_col_name = "admin_id";
    public static final String roleId_col_name = "role_id";


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
     * role表主键
     */
    @ApiModelProperty(value = "role表主键")
    private Long roleId;

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