package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户职权和资源的映射表.(MappingAdminAuthorityToResource)表实体类
 *
 * @author like
 * @since 2021-06-04 13:13:57
 */
@Data
@TableName("mapping_admin_authority_to_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户职权和资源的映射表.")
@SuppressWarnings("serial")
public class MappingAdminAuthorityToResource extends BaseEntity<MappingAdminAuthorityToResource> {

    public static final String adminAuthorityId_col_name = "admin_authority_id";
    public static final String resourceId_col_name = "resource_id";


    /**
     * 用户职权 ID
     */
    @ApiModelProperty(value = "用户职权 ID")
    private String adminAuthorityId;

    /**
     * 资源 ID
     */
    @ApiModelProperty(value = "资源 ID")
    private String resourceId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.adminAuthorityId;
    }

}