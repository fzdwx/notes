package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingClientAuthorityToResource;
import cn.like.code.service.MappingClientAuthorityToResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * (MappingClientAuthorityToResource)表控制层
 *
 * @author like
 * @since 2021-06-04 13:14:01
 */
@RestController
@RequestMapping("mappingClientAuthorityToResource")
@RequiredArgsConstructor
public class MappingClientAuthorityToResourceController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingClientAuthorityToResourceService mappingClientAuthorityToResourceService;


    /**
     * 分页查询所有数据
     *
     * @param page                             分页对象
     * @param mappingClientAuthorityToResource 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingClientAuthorityToResourcePage(Page<MappingClientAuthorityToResource> page, MappingClientAuthorityToResource mappingClientAuthorityToResource) {
        return ResponseEntity.ok((this.mappingClientAuthorityToResourceService
                .page(page, new QueryWrapper<>(mappingClientAuthorityToResource))));
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
        return ResponseEntity.ok(this.mappingClientAuthorityToResourceService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingClientAuthorityToResource 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingClientAuthorityToResource mappingClientAuthorityToResource) {
        return ResponseEntity.ok(this.mappingClientAuthorityToResourceService.save(mappingClientAuthorityToResource));
    }


    /**
     * 修改
     *
     * @param mappingClientAuthorityToResource
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingClientAuthorityToResource mappingClientAuthorityToResource) {
        return ResponseEntity
                .ok(this.mappingClientAuthorityToResourceService.updateById(mappingClientAuthorityToResource));
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
        return ResponseEntity.ok(this.mappingClientAuthorityToResourceService.removeById(id));
    }

}