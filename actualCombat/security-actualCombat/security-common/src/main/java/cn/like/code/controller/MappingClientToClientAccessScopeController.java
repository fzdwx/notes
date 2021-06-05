package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingClientToClientAccessScope;
import cn.like.code.service.MappingClientToClientAccessScopeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * (MappingClientToClientAccessScope)表控制层
 *
 * @author pig4cloud
 * @since 2021-06-05 09:24:22
 */
@RestController
@RequestMapping("mappingClientToClientAccessScope")
@RequiredArgsConstructor
public class MappingClientToClientAccessScopeController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingClientToClientAccessScopeService mappingClientToClientAccessScopeService;


    /**
     * 分页查询所有数据
     *
     * @param page                             分页对象
     * @param mappingClientToClientAccessScope 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingClientToClientAccessScopePage(Page<MappingClientToClientAccessScope> page, MappingClientToClientAccessScope mappingClientToClientAccessScope) {
        return ResponseEntity.ok((this.mappingClientToClientAccessScopeService.page(page, new QueryWrapper<>(mappingClientToClientAccessScope))));
    }


    /**
     * 通过id查询
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToClientAccessScopeService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingClientToClientAccessScope 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingClientToClientAccessScope mappingClientToClientAccessScope) {
        return ResponseEntity.ok(this.mappingClientToClientAccessScopeService.save(mappingClientToClientAccessScope));
    }


    /**
     * 修改
     *
     * @param mappingClientToClientAccessScope
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingClientToClientAccessScope mappingClientToClientAccessScope) {
        return ResponseEntity.ok(this.mappingClientToClientAccessScopeService.updateById(mappingClientToClientAccessScope));
    }


    /**
     * 通过id删除
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToClientAccessScopeService.removeById(id));
    }

}