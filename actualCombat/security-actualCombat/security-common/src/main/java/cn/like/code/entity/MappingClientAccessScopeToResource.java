package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (MappingClientAccessScopeToResource)表实体类
 *
 * @author pig4cloud
 * @since 2021-06-05 09:24:08
 */
@Data
@TableName("mapping_client_access_scope_to_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
@SuppressWarnings("serial")
public class MappingClientAccessScopeToResource extends BaseEntity<MappingClientAccessScopeToResource> {

    public static final String clientAccessScopeId_col_name = "client_access_scope_id";
    public static final String resourceId_col_name = "resource_id";


    /**
     * 客户端访问范围 ID
     */
    @ApiModelProperty(value = "客户端访问范围 ID")
    private String clientAccessScopeId;

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
        return this.clientAccessScopeId;
    }

}