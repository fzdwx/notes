package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Permission;
import cn.like.code.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 后台用户权限表(Permission)表控制层
 *
 * @author like
 * @since 2021-06-02 12:23:55
 */
@RestController
@RequestMapping("permission")
@RequiredArgsConstructor
public class PermissionController extends BaseController {
    /**
     * 服务对象
     */
    private final PermissionService permissionService;


    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param permission 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getPermissionPage(Page<Permission> page, Permission permission) {
        return ResponseEntity.ok((this.permissionService.page(page, new QueryWrapper<>(permission))));
    }


    /**
     * 通过id查询后台用户权限表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.permissionService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param permission 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增后台用户权限表", notes = "新增后台用户权限表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Permission permission) {
        return ResponseEntity.ok(this.permissionService.save(permission));
    }


    /**
     * 修改后台用户权限表
     *
     * @param permission 后台用户权限表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改后台用户权限表", notes = "修改后台用户权限表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Permission permission) {
        return ResponseEntity.ok(this.permissionService.updateById(permission));
    }


    /**
     * 通过id删除后台用户权限表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除后台用户权限表", notes = "通过id删除后台用户权限表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.permissionService.removeById(id));
    }

}