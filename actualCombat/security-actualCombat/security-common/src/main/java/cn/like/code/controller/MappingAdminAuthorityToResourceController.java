package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingAdminAuthorityToResource;
import cn.like.code.service.MappingAdminAuthorityToResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 用户职权和资源的映射表.(MappingAdminAuthorityToResource)表控制层
 *
 * @author like
 * @since 2021-06-04 13:13:57
 */
@RestController
@RequestMapping("mappingAdminAuthorityToResource")
@RequiredArgsConstructor
public class MappingAdminAuthorityToResourceController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingAdminAuthorityToResourceService mappingAdminAuthorityToResourceService;


    /**
     * 分页查询所有数据
     *
     * @param page                            分页对象
     * @param mappingAdminAuthorityToResource 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingAdminAuthorityToResourcePage(Page<MappingAdminAuthorityToResource> page, MappingAdminAuthorityToResource mappingAdminAuthorityToResource) {
        return ResponseEntity.ok((this.mappingAdminAuthorityToResourceService
                .page(page, new QueryWrapper<>(mappingAdminAuthorityToResource))));
    }


    /**
     * 通过id查询用户职权和资源的映射表.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingAdminAuthorityToResourceService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingAdminAuthorityToResource 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增用户职权和资源的映射表.", notes = "新增用户职权和资源的映射表.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingAdminAuthorityToResource mappingAdminAuthorityToResource) {
        return ResponseEntity.ok(this.mappingAdminAuthorityToResourceService.save(mappingAdminAuthorityToResource));
    }


    /**
     * 修改用户职权和资源的映射表.
     *
     * @param mappingAdminAuthorityToResource 用户职权和资源的映射表.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户职权和资源的映射表.", notes = "修改用户职权和资源的映射表.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingAdminAuthorityToResource mappingAdminAuthorityToResource) {
        return ResponseEntity
                .ok(this.mappingAdminAuthorityToResourceService.updateById(mappingAdminAuthorityToResource));
    }


    /**
     * 通过id删除用户职权和资源的映射表.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除用户职权和资源的映射表.", notes = "通过id删除用户职权和资源的映射表.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingAdminAuthorityToResourceService.removeById(id));
    }

}