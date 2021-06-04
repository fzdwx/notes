package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 资源. 代表着形如 /user/1 的具体的资源本身.(Resource)表实体类
 *
 * @author like
 * @since 2021-06-04 13:14:07
 */
@Data
@TableName("resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资源. 代表着形如 /user/1 的具体的资源本身.")
@SuppressWarnings("serial")
public class Resource extends BaseEntity<Resource> {

    public static final String id_col_name = "id";
    public static final String endpoint_col_name = "endpoint";


    /**
     * 标识资源的 ID
     */
    @ApiModelProperty(value = "标识资源的 ID")
    private String id;

    /**
     * 资源端点
     */
    @ApiModelProperty(value = "资源端点")
    private String endpoint;

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