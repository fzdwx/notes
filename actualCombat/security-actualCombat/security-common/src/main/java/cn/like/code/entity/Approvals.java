package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 批准的用户(Approvals)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:13
 */
@Data
@TableName("approvals")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "批准的用户")
@SuppressWarnings("serial")
public class Approvals extends BaseEntity<Approvals> {

    public static final String username_col_name = "username";
    public static final String clientId_col_name = "client_id";
    public static final String scope_col_name = "scope";
    public static final String status_col_name = "status";
    public static final String expiresat_col_name = "expiresat";
    public static final String lastmodifiedat_col_name = "lastmodifiedat";


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 客户端表主键id
     */
    @ApiModelProperty(value = "客户端表主键id")
    private String clientId;

    /**
     * 使用范围
     */
    @ApiModelProperty(value = "使用范围")
    private String scope;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expiresat;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间")
    private LocalDateTime lastmodifiedat;


}