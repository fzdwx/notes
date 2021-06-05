package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户职权和资源的映射表.(MappingUserAuthorityToResource)表实体类
 *
 * @author pig4cloud
 * @since 2021-06-05 10:05:01
 */
@Data
@TableName("mapping_user_authority_to_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户职权和资源的映射表.")
@SuppressWarnings("serial")
public class MappingUserAuthorityToResource extends BaseEntity<MappingUserAuthorityToResource> {

    public static final String userAuthorityId_col_name = "user_authority_id";
    public static final String resourceId_col_name = "resource_id";


    /**
     * 用户职权 ID
     */
    @ApiModelProperty(value = "用户职权 ID")
    private String userAuthorityId;

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
        return this.userAuthorityId;
    }

}