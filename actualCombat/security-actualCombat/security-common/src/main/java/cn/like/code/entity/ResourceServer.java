package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 资源服务器. 可提供客户端访问的资源服务器定义.(ResourceServer)表实体类
 *
 * @author like
 * @since 2021-06-04 13:14:08
 */
@Data
@TableName("resource_server")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资源服务器. 可提供客户端访问的资源服务器定义.")
@SuppressWarnings("serial")
public class ResourceServer extends BaseEntity<ResourceServer> {

    public static final String id_col_name = "id";
    public static final String resourceSecret_col_name = "resource_secret";
    public static final String description_col_name = "description";


    /**
     * 资源服务器 ID
     */
    @ApiModelProperty(value = "资源服务器 ID")
    private String id;

    /**
     * 资源密钥 (加密后)
     */
    @ApiModelProperty(value = "资源密钥 (加密后)")
    private String resourceSecret;

    /**
     * 资源服务器描述
     */
    @ApiModelProperty(value = "资源服务器描述")
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