package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 访问令牌(Token)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:22
 */
@Data
@TableName("token")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "访问令牌")
@SuppressWarnings("serial")
public class Token extends BaseEntity<Token> {

    public static final String id_col_name = "id";
    public static final String accessToken_col_name = "access_token";
    public static final String refreshToken_col_name = "refresh_token";
    public static final String username_col_name = "username";
    public static final String clientId_col_name = "client_id";
    public static final String authentication_col_name = "authentication";


    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * access token
     */
    @ApiModelProperty(value = "access token")
    private String accessToken;

    /**
     * refresh token
     */
    @ApiModelProperty(value = "refresh token")
    private String refreshToken;

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
     * 验证
     */
    @ApiModelProperty(value = "验证")
    private Object authentication;

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