package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingClientAccessScopeToResource;
import cn.like.code.service.MappingClientAccessScopeToResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * (MappingClientAccessScopeToResource)表控制层
 *
 * @author pig4cloud
 * @since 2021-06-05 09:24:09
 */
@RestController
@RequestMapping("mappingClientAccessScopeToResource")
@RequiredArgsConstructor
public class MappingClientAccessScopeToResourceController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingClientAccessScopeToResourceService mappingClientAccessScopeToResourceService;


    /**
     * 分页查询所有数据
     *
     * @param page                               分页对象
     * @param mappingClientAccessScopeToResource 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingClientAccessScopeToResourcePage(Page<MappingClientAccessScopeToResource> page, MappingClientAccessScopeToResource mappingClientAccessScopeToResource) {
        return ResponseEntity.ok((this.mappingClientAccessScopeToResourceService.page(page, new QueryWrapper<>(mappingClientAccessScopeToResource))));
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
        return ResponseEntity.ok(this.mappingClientAccessScopeToResourceService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingClientAccessScopeToResource 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingClientAccessScopeToResource mappingClientAccessScopeToResource) {
        return ResponseEntity.ok(this.mappingClientAccessScopeToResourceService.save(mappingClientAccessScopeToResource));
    }


    /**
     * 修改
     *
     * @param mappingClientAccessScopeToResource
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingClientAccessScopeToResource mappingClientAccessScopeToResource) {
        return ResponseEntity.ok(this.mappingClientAccessScopeToResourceService.updateById(mappingClientAccessScopeToResource));
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
        return ResponseEntity.ok(this.mappingClientAccessScopeToResourceService.removeById(id));
    }

}