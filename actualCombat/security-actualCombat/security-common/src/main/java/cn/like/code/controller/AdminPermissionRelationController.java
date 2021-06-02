package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.AdminPermissionRelation;
import cn.like.code.service.AdminPermissionRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)(AdminPermissionRelation)表控制层
 *
 * @author like
 * @since 2021-06-02 12:23:50
 */
@RestController
@RequestMapping("adminPermissionRelation")
@RequiredArgsConstructor
public class AdminPermissionRelationController extends BaseController {
    /**
     * 服务对象
     */
    private final AdminPermissionRelationService adminPermissionRelationService;


    /**
     * 分页查询所有数据
     *
     * @param page                    分页对象
     * @param adminPermissionRelation 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getAdminPermissionRelationPage(Page<AdminPermissionRelation> page, AdminPermissionRelation adminPermissionRelation) {
        return ResponseEntity
                .ok((this.adminPermissionRelationService.page(page, new QueryWrapper<>(adminPermissionRelation))));
    }


    /**
     * 通过id查询后台用户和权限关系表(除角色中定义的权限以外的加减权限)
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminPermissionRelationService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param adminPermissionRelation 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增后台用户和权限关系表(除角色中定义的权限以外的加减权限)", notes = "新增后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AdminPermissionRelation adminPermissionRelation) {
        return ResponseEntity.ok(this.adminPermissionRelationService.save(adminPermissionRelation));
    }


    /**
     * 修改后台用户和权限关系表(除角色中定义的权限以外的加减权限)
     *
     * @param adminPermissionRelation 后台用户和权限关系表(除角色中定义的权限以外的加减权限)
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改后台用户和权限关系表(除角色中定义的权限以外的加减权限)", notes = "修改后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody AdminPermissionRelation adminPermissionRelation) {
        return ResponseEntity.ok(this.adminPermissionRelationService.updateById(adminPermissionRelation));
    }


    /**
     * 通过id删除后台用户和权限关系表(除角色中定义的权限以外的加减权限)
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除后台用户和权限关系表(除角色中定义的权限以外的加减权限)", notes = "通过id删除后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminPermissionRelationService.removeById(id));
    }

}