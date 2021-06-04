package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingClientToResourceServer;
import cn.like.code.service.MappingClientToResourceServerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.(MappingClientToResourceServer)表控制层
 *
 * @author like
 * @since 2021-06-04 13:14:05
 */
@RestController
@RequestMapping("mappingClientToResourceServer")
@RequiredArgsConstructor
public class MappingClientToResourceServerController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingClientToResourceServerService mappingClientToResourceServerService;


    /**
     * 分页查询所有数据
     *
     * @param page                          分页对象
     * @param mappingClientToResourceServer 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingClientToResourceServerPage(Page<MappingClientToResourceServer> page, MappingClientToResourceServer mappingClientToResourceServer) {
        return ResponseEntity.ok((this.mappingClientToResourceServerService
                .page(page, new QueryWrapper<>(mappingClientToResourceServer))));
    }


    /**
     * 通过id查询客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToResourceServerService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingClientToResourceServer 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.", notes = "新增客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingClientToResourceServer mappingClientToResourceServer) {
        return ResponseEntity.ok(this.mappingClientToResourceServerService.save(mappingClientToResourceServer));
    }


    /**
     * 修改客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.
     *
     * @param mappingClientToResourceServer 客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.", notes = "修改客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingClientToResourceServer mappingClientToResourceServer) {
        return ResponseEntity.ok(this.mappingClientToResourceServerService.updateById(mappingClientToResourceServer));
    }


    /**
     * 通过id删除客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.", notes = "通过id删除客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingClientToResourceServerService.removeById(id));
    }

}