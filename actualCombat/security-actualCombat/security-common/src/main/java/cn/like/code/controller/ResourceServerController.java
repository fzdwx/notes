package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.ResourceServer;
import cn.like.code.service.ResourceServerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 资源服务器. 可提供客户端访问的资源服务器定义.(ResourceServer)表控制层
 *
 * @author like
 * @since 2021-06-04 13:14:09
 */
@RestController
@RequestMapping("resourceServer")
@RequiredArgsConstructor
public class ResourceServerController extends BaseController {
    /**
     * 服务对象
     */
    private final ResourceServerService resourceServerService;


    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param resourceServer 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getResourceServerPage(Page<ResourceServer> page, ResourceServer resourceServer) {
        return ResponseEntity.ok((this.resourceServerService.page(page, new QueryWrapper<>(resourceServer))));
    }


    /**
     * 通过id查询资源服务器. 可提供客户端访问的资源服务器定义.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.resourceServerService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param resourceServer 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增资源服务器. 可提供客户端访问的资源服务器定义.", notes = "新增资源服务器. 可提供客户端访问的资源服务器定义.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ResourceServer resourceServer) {
        return ResponseEntity.ok(this.resourceServerService.save(resourceServer));
    }


    /**
     * 修改资源服务器. 可提供客户端访问的资源服务器定义.
     *
     * @param resourceServer 资源服务器. 可提供客户端访问的资源服务器定义.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改资源服务器. 可提供客户端访问的资源服务器定义.", notes = "修改资源服务器. 可提供客户端访问的资源服务器定义.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody ResourceServer resourceServer) {
        return ResponseEntity.ok(this.resourceServerService.updateById(resourceServer));
    }


    /**
     * 通过id删除资源服务器. 可提供客户端访问的资源服务器定义.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除资源服务器. 可提供客户端访问的资源服务器定义.", notes = "通过id删除资源服务器. 可提供客户端访问的资源服务器定义.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.resourceServerService.removeById(id));
    }

}