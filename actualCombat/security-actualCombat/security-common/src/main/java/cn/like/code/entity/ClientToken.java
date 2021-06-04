package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户token表(ClientToken)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:19
 */
@Data
@TableName("client_token")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户token表")
@SuppressWarnings("serial")
public class ClientToken extends BaseEntity<ClientToken> {

    public static final String authenticationId_col_name = "authentication_id";
    public static final String tokenId_col_name = "token_id";
    public static final String username_col_name = "username";
    public static final String clientId_col_name = "client_id";


    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String authenticationId;

    /**
     * token ID
     */
    @ApiModelProperty(value = "token ID")
    private String tokenId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String username;

    /**
     * client_details 表主键
     */
    @ApiModelProperty(value = "client_details 表主键")
    private String clientId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.authenticationId;
    }

}