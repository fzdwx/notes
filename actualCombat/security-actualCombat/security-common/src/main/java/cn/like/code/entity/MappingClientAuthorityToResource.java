package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (MappingClientAuthorityToResource)表实体类
 *
 * @author like
 * @since 2021-06-04 13:14:01
 */
@Data
@TableName("mapping_client_authority_to_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
@SuppressWarnings("serial")
public class MappingClientAuthorityToResource extends BaseEntity<MappingClientAuthorityToResource> {

    public static final String clientAuthorityId_col_name = "client_authority_id";
    public static final String resourceId_col_name = "resource_id";


    /**
     * 客户端职权 ID
     */
    @ApiModelProperty(value = "客户端职权 ID")
    private String clientAuthorityId;

    /**
     * 标识资源的 ID
     */
    @ApiModelProperty(value = "标识资源的 ID")
    private String resourceId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.clientAuthorityId;
    }

}