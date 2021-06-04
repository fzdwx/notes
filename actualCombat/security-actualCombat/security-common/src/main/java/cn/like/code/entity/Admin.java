package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 后台用户表(Admin)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:09
 */
@Data
@TableName("admin")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户表")
@SuppressWarnings("serial")
public class Admin extends BaseEntity<Admin> {

    public static final String id_col_name = "id";
    public static final String username_col_name = "username";
    public static final String password_col_name = "password";
    public static final String icon_col_name = "icon";
    public static final String email_col_name = "email";
    public static final String nickName_col_name = "nick_name";
    public static final String note_col_name = "note";
    public static final String createTime_col_name = "create_time";
    public static final String loginTime_col_name = "login_time";
    public static final String status_col_name = "status";


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String icon;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String note;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

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