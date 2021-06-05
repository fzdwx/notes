package cn.like.code.controller;


import cn.hutool.json.JSONUtil;
import cn.like.code.base.BaseController;
import cn.like.code.entity.Resource;
import cn.like.code.service.ResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 资源. 代表着形如 /user/1 的具体的资源本身.(Resource)表控制层
 *
 * @author like
 * @since 2021-06-04 13:14:07
 */
@RestController
@RequestMapping("resource")
@RequiredArgsConstructor
@Slf4j
public class ResourceController extends BaseController {
    /**
     * 服务对象
     */
    private final ResourceService resourceService;

    @GetMapping("/access")
    public String access() {
        final String securityContext = JSONUtil.toJsonStr(SecurityContextHolder.getContext());
        log.debug("resource-server accessed with security context: ");
        log.debug(securityContext);
        return securityContext;
    }

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param resource 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getResourcePage(Page<Resource> page, Resource resource) {
        return ResponseEntity.ok((this.resourceService.page(page, new QueryWrapper<>(resource))));
    }


    /**
     * 通过id查询资源. 代表着形如 /user/1 的具体的资源本身.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.resourceService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param resource 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增资源. 代表着形如 /user/1 的具体的资源本身.", notes = "新增资源. 代表着形如 /user/1 的具体的资源本身.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Resource resource) {
        return ResponseEntity.ok(this.resourceService.save(resource));
    }


    /**
     * 修改资源. 代表着形如 /user/1 的具体的资源本身.
     *
     * @param resource 资源. 代表着形如 /user/1 的具体的资源本身.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改资源. 代表着形如 /user/1 的具体的资源本身.", notes = "修改资源. 代表着形如 /user/1 的具体的资源本身.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Resource resource) {
        return ResponseEntity.ok(this.resourceService.updateById(resource));
    }


    /**
     * 通过id删除资源. 代表着形如 /user/1 的具体的资源本身.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除资源. 代表着形如 /user/1 的具体的资源本身.", notes = "通过id删除资源. 代表着形如 /user/1 的具体的资源本身.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.resourceService.removeById(id));
    }

}