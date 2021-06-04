package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权码(Code)表实体类
 *
 * @author like
 * @since 2021-06-04 09:09:20
 */
@Data
@TableName("code")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "授权码")
@SuppressWarnings("serial")
public class Code extends BaseEntity<Code> {

    public static final String code_col_name = "code";
    public static final String authentication_col_name = "authentication";


    /**
     * 授权码模式的code
     */
    @ApiModelProperty(value = "授权码模式的code")
    private String code;

    /**
     * 验证
     */
    @ApiModelProperty(value = "验证")
    private String authentication;


}