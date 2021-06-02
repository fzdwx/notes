package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Admin;
import cn.like.code.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 后台用户表(Admin)表控制层
 *
 * @author like
 * @since 2021-06-02 12:23:48
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {
    /**
     * 服务对象
     */
    private final AdminService adminService;


    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param admin 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getAdminPage(Page<Admin> page, Admin admin) {
        return ResponseEntity.ok((this.adminService.page(page, new QueryWrapper<>(admin))));
    }


    /**
     * 通过id查询后台用户表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param admin 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增后台用户表", notes = "新增后台用户表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Admin admin) {
        return ResponseEntity.ok(this.adminService.save(admin));
    }


    /**
     * 修改后台用户表
     *
     * @param admin 后台用户表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改后台用户表", notes = "修改后台用户表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Admin admin) {
        return ResponseEntity.ok(this.adminService.updateById(admin));
    }


    /**
     * 通过id删除后台用户表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除后台用户表", notes = "通过id删除后台用户表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminService.removeById(id));
    }

}