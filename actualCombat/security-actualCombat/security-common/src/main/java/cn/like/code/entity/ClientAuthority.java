package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).(ClientAuthority)表实体类
 *
 * @author like
 * @since 2021-06-04 13:13:54
 */
@Data
@TableName("client_authority")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).")
@SuppressWarnings("serial")
public class ClientAuthority extends BaseEntity<ClientAuthority> {

    public static final String id_col_name = "id";
    public static final String name_col_name = "name";
    public static final String description_col_name = "description";


    /**
     * 客户端职权 ID
     */
    @ApiModelProperty(value = "客户端职权 ID")
    private String id;

    /**
     * 职权名称
     */
    @ApiModelProperty(value = "职权名称")
    private String name;

    /**
     * 职权描述
     */
    @ApiModelProperty(value = "职权描述")
    private String description;

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