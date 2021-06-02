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
 * 后台用户权限表(Permission)表实体类
 *
 * @author like
 * @since 2021-06-02 12:23:54
 */
@Data
@TableName("permission")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "后台用户权限表")
@SuppressWarnings("serial")
public class Permission extends BaseEntity<Permission> {

    public static final String id_col_name = "id";
    public static final String pid_col_name = "pid";
    public static final String name_col_name = "name";
    public static final String value_col_name = "value";
    public static final String icon_col_name = "icon";
    public static final String type_col_name = "type";
    public static final String uri_col_name = "uri";
    public static final String status_col_name = "status";
    public static final String createTime_col_name = "create_time";
    public static final String sort_col_name = "sort";

    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 父级权限id
     */
    @ApiModelProperty(value = "父级权限id")
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    private String value;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    /**
     * 前端资源路径
     */
    @ApiModelProperty(value = "前端资源路径")
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     */
    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

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