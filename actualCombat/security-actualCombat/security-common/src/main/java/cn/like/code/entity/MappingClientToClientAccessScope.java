package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (MappingClientToClientAccessScope)表实体类
 *
 * @author pig4cloud
 * @since 2021-06-05 09:24:21
 */
@Data
@TableName("mapping_client_to_client_access_scope")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
@SuppressWarnings("serial")
public class MappingClientToClientAccessScope extends BaseEntity<MappingClientToClientAccessScope> {

    public static final String clientId_col_name = "client_id";
    public static final String clientAccessScopeId_col_name = "client_access_scope_id";


    /**
     * 客户端 ID
     */
    @ApiModelProperty(value = "客户端 ID")
    private String clientId;

    /**
     * 客户端访问范围 ID
     */
    @ApiModelProperty(value = "客户端访问范围 ID")
    private String clientAccessScopeId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.clientId;
    }

}