package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户端到客户端职权的映射表.(MappingClientToClientAuthority)表实体类
 *
 * @author like
 * @since 2021-06-04 13:14:03
 */
@Data
@TableName("mapping_client_to_client_authority")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户端到客户端职权的映射表.")
@SuppressWarnings("serial")
public class MappingClientToClientAuthority extends BaseEntity<MappingClientToClientAuthority> {

    public static final String clientId_col_name = "client_id";
    public static final String clientAuthorityId_col_name = "client_authority_id";


    /**
     * 客户端 ID
     */
    @ApiModelProperty(value = "客户端 ID")
    private String clientId;

    /**
     * 客户端职权 ID
     */
    @ApiModelProperty(value = "客户端职权 ID")
    private String clientAuthorityId;

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