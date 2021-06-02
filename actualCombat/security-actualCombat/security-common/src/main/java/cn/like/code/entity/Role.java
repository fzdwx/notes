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
 * 后台用户角色表(Role)表实体类
 *
 * @author like
 * @since 2021-06-02 12:23:56
 */
@Data
@TableName("role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户角色表")
@SuppressWarnings("serial")
public class Role extends BaseEntity<Role> {

    public static final String id_col_name = "id";
    public static final String name_col_name = "name";
    public static final String description_col_name = "description";
    public static final String adminCount_col_name = "admin_count";
    public static final String createTime_col_name = "create_time";
    public static final String status_col_name = "status";
    public static final String sort_col_name = "sort";


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 后台用户数量
     */
    @ApiModelProperty(value = "后台用户数量")
    private Integer adminCount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 启用状态：0->禁用；1->启用
     */
    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

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