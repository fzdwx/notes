package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (ClientAccessScope)表实体类
 *
 * @author pig4cloud
 * @since 2021-06-05 09:23:33
 */
@Data
@TableName("client_access_scope")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
@SuppressWarnings("serial")
public class ClientAccessScope extends BaseEntity<ClientAccessScope> {

    public static final String id_col_name = "id";
    public static final String scope_col_name = "scope";


    /**
     * 客户端访问范围 ID
     */
    @ApiModelProperty(value = "客户端访问范围 ID")
    private String id;

    /**
     * 客户端访问范围编码
     */
    @ApiModelProperty(value = "客户端访问范围编码")
    private String scope;

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