package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 认证客户端详情表(ClientDetails)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:17
 */
@Data
@TableName("client_details")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "认证客户端详情表")
@SuppressWarnings("serial")
public class ClientDetails extends BaseEntity<ClientDetails> {

    public static final String clientId_col_name = "client_id";
    public static final String resourceIds_col_name = "resource_ids";
    public static final String clientSecret_col_name = "client_secret";
    public static final String scope_col_name = "scope";
    public static final String authorizedGrantTypes_col_name = "authorized_grant_types";
    public static final String webServerRedirectUri_col_name = "web_server_redirect_uri";
    public static final String authorities_col_name = "authorities";
    public static final String accessTokenValidity_col_name = "access_token_validity";
    public static final String refreshTokenValidity_col_name = "refresh_token_validity";
    public static final String additionalInformation_col_name = "additional_information";
    public static final String autoapprove_col_name = "autoapprove";


    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private String clientId;

    /**
     * 资源服务器ids(例如后台，api接口)
     */
    @ApiModelProperty(value = "资源服务器ids(例如后台，api接口)")
    private String resourceIds;

    /**
     * 客户端密码
     */
    @ApiModelProperty(value = "客户端密码")
    private String clientSecret;

    /**
     * 范围
     */
    @ApiModelProperty(value = "范围")
    private String scope;

    /**
     * 认证方式例如authorization_code,password,refresh_token
     */
    @ApiModelProperty(value = "认证方式例如authorization_code,password,refresh_token")
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private String authorities;

    /**
     * token有效时间
     */
    @ApiModelProperty(value = "token有效时间")
    private Integer accessTokenValidity;

    /**
     * refresh token有效时间
     */
    @ApiModelProperty(value = "refresh token有效时间")
    private Integer refreshTokenValidity;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String additionalInformation;

    /**
     * auto approve
     */
    @ApiModelProperty(value = "auto approve")
    private String autoapprove;

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