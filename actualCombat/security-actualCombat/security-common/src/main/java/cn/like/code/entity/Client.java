package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户端(Client)表实体类
 *
 * @author like
 * @since 2021-06-04 13:13:52
 */
@Data
@TableName("client")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户端")
@SuppressWarnings("serial")
public class Client extends BaseEntity<Client> {

    public static final String clientId_col_name = "clientId";
    public static final String clientSecret_col_name = "client_secret";
    public static final String scope_col_name = "scope";
    public static final String authorizedGrantType_col_name = "authorized_grant_type";
    public static final String redirectUri_col_name = "redirect_uri";
    public static final String accessTokenValidity_col_name = "access_token_validity";
    public static final String refreshTokenValidity_col_name = "refresh_token_validity";
    public static final String autoApprove_col_name = "auto_approve";
    public static final String description_col_name = "description";


    /**
     * 客户端 名字
     */
    @ApiModelProperty(value = "客户端 名字")
    private String clientId;

    /**
     * 客户端 Secret (加密后)
     */
    @ApiModelProperty(value = "客户端 Secret (加密后)")
    private String clientSecret;

    /**
     * 客户端 Scope (英文逗号分隔)
     */
    @ApiModelProperty(value = "客户端 Scope (英文逗号分隔)")
    private String scope;

    /**
     * 授权方式, 只可能是: authorization_code,implicit,refresh_token,password,client_credentials.
     * 如果是多个, 以英文逗号分隔.
     */
    @ApiModelProperty(value = "授权方式, 只可能是: authorization_code,implicit,refresh_token,password,client_credentials. 如果是多个, 以英文逗号分隔.")
    private String authorizedGrantType;

    /**
     * 重定向地址, 当授权方式是 authorization_code 时有效. 如果有多个, 按英文逗号分隔.
     */
    @ApiModelProperty(value = "重定向地址, 当授权方式是 authorization_code 时有效. 如果有多个, 按英文逗号分隔.")
    private String redirectUri;

    /**
     * access-token 过期时间 (秒)
     */
    @ApiModelProperty(value = "access-token 过期时间 (秒)")
    private Integer accessTokenValidity;

    /**
     * refresh-token 过期时间 (秒)
     */
    @ApiModelProperty(value = "refresh-token 过期时间 (秒)")
    private Integer refreshTokenValidity;

    /**
     * 是否自动允许
     */
    @ApiModelProperty(value = "是否自动允许")
    private boolean autoApprove;

    /**
     * 客户端描述
     */
    @ApiModelProperty(value = "客户端描述")
    private String description;

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