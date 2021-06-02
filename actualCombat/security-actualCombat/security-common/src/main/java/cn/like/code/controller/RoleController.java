package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Role;
import cn.like.code.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 后台用户角色表(Role)表控制层
 *
 * @author like
 * @since 2021-06-02 12:23:57
 */
@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    /**
     * 服务对象
     */
    private final RoleService roleService;


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param role 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getRolePage(Page<Role> page, Role role) {
        return ResponseEntity.ok((this.roleService.page(page, new QueryWrapper<>(role))));
    }


    /**
     * 通过id查询后台用户角色表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.roleService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param role 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增后台用户角色表", notes = "新增后台用户角色表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Role role) {
        return ResponseEntity.ok(this.roleService.save(role));
    }


    /**
     * 修改后台用户角色表
     *
     * @param role 后台用户角色表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改后台用户角色表", notes = "修改后台用户角色表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Role role) {
        return ResponseEntity.ok(this.roleService.updateById(role));
    }


    /**
     * 通过id删除后台用户角色表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除后台用户角色表", notes = "通过id删除后台用户角色表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.roleService.removeById(id));
    }

}