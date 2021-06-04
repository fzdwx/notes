package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingClientToClientAuthority;
import cn.like.code.service.MappingClientToClientAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 客户端到客户端职权的映射表.(MappingClientToClientAuthority)表控制层
 *
 * @author like
 * @since 2021-06-04 13:14:03
 */
@RestController
@RequestMapping("mappingClientToClientAuthority")
@RequiredArgsConstructor
public class MappingClientToClientAuthorityController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingClientToClientAuthorityService mappingClientToClientAuthorityService;


    /**
     * 分页查询所有数据
     *
     * @param page                           分页对象
     * @param mappingClientToClientAuthority 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingClientToClientAuthorityPage(Page<MappingClientToClientAuthority> page, MappingClientToClientAuthority mappingClientToClientAuthority) {
        return ResponseEntity.ok((this.mappingClientToClientAuthorityService
                .page(page, new QueryWrapper<>(mappingClientToClientAuthority))));
    }


    /**
     * 通过id查询客户端到客户端职权的映射表.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToClientAuthorityService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingClientToClientAuthority 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增客户端到客户端职权的映射表.", notes = "新增客户端到客户端职权的映射表.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingClientToClientAuthority mappingClientToClientAuthority) {
        return ResponseEntity.ok(this.mappingClientToClientAuthorityService.save(mappingClientToClientAuthority));
    }


    /**
     * 修改客户端到客户端职权的映射表.
     *
     * @param mappingClientToClientAuthority 客户端到客户端职权的映射表.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改客户端到客户端职权的映射表.", notes = "修改客户端到客户端职权的映射表.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingClientToClientAuthority mappingClientToClientAuthority) {
        return ResponseEntity.ok(this.mappingClientToClientAuthorityService.updateById(mappingClientToClientAuthority));
    }


    /**
     * 通过id删除客户端到客户端职权的映射表.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除客户端到客户端职权的映射表.", notes = "通过id删除客户端到客户端职权的映射表.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToClientAuthorityService.removeById(id));
    }

}