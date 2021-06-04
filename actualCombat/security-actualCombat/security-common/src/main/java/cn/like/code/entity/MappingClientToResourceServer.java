package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.(MappingClientToResourceServer)表实体类
 *
 * @author like
 * @since 2021-06-04 13:14:05
 */
@Data
@TableName("mapping_client_to_resource_server")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.")
@SuppressWarnings("serial")
public class MappingClientToResourceServer extends BaseEntity<MappingClientToResourceServer> {

    public static final String clientId_col_name = "client_id";
    public static final String resourceServerId_col_name = "resource_server_id";


    /**
     * 客户端 ID
     */
    @ApiModelProperty(value = "客户端 ID")
    private String clientId;

    /**
     * 资源服务器 ID
     */
    @ApiModelProperty(value = "资源服务器 ID")
    private String resourceServerId;

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