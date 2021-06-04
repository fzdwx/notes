package cn.like.code.entity;

import cn.like.code.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户和用户职权的映射表.(MappingAdminToUserAuthority)表实体类
 *
 * @author like
 * @since 2021-06-04 13:13:59
 */
@Data
@TableName("mapping_admin_to_user_authority")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户和用户职权的映射表.")
@SuppressWarnings("serial")
public class MappingAdminToUserAuthority extends BaseEntity<MappingAdminToUserAuthority> {

    public static final String userId_col_name = "user_id";
    public static final String userAuthorityId_col_name = "user_authority_id";


    /**
     * 用户 ID
     */
    @ApiModelProperty(value = "用户 ID")
    private String userId;

    /**
     * 用户职权 ID
     */
    @ApiModelProperty(value = "用户职权 ID")
    private Object userAuthorityId;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.userId;
    }

}