package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.AdminRoleRelation;
import cn.like.code.service.AdminRoleRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 后台用户和角色关系表(AdminRoleRelation)表控制层
 *
 * @author like
 * @since 2021-06-02 12:23:52
 */
@RestController
@RequestMapping("adminRoleRelation")
@RequiredArgsConstructor
public class AdminRoleRelationController extends BaseController {
    /**
     * 服务对象
     */
    private final AdminRoleRelationService adminRoleRelationService;


    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param adminRoleRelation 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getAdminRoleRelationPage(Page<AdminRoleRelation> page, AdminRoleRelation adminRoleRelation) {
        return ResponseEntity.ok((this.adminRoleRelationService.page(page, new QueryWrapper<>(adminRoleRelation))));
    }


    /**
     * 通过id查询后台用户和角色关系表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminRoleRelationService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param adminRoleRelation 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增后台用户和角色关系表", notes = "新增后台用户和角色关系表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AdminRoleRelation adminRoleRelation) {
        return ResponseEntity.ok(this.adminRoleRelationService.save(adminRoleRelation));
    }


    /**
     * 修改后台用户和角色关系表
     *
     * @param adminRoleRelation 后台用户和角色关系表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改后台用户和角色关系表", notes = "修改后台用户和角色关系表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody AdminRoleRelation adminRoleRelation) {
        return ResponseEntity.ok(this.adminRoleRelationService.updateById(adminRoleRelation));
    }


    /**
     * 通过id删除后台用户和角色关系表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除后台用户和角色关系表", notes = "通过id删除后台用户和角色关系表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminRoleRelationService.removeById(id));
    }

}