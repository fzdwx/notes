package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Authorities;
import cn.like.code.service.AuthoritiesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 用户权限(Authorities)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:15
 */
@RestController
@RequestMapping("authorities")
@RequiredArgsConstructor
public class AuthoritiesController extends BaseController {
    /**
     * 服务对象
     */
    private final AuthoritiesService authoritiesService;


    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param authorities 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getAuthoritiesPage(Page<Authorities> page, Authorities authorities) {
        return ResponseEntity.ok((this.authoritiesService.page(page, new QueryWrapper<>(authorities))));
    }


    /**
     * 通过id查询用户权限
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.authoritiesService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param authorities 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增用户权限", notes = "新增用户权限")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Authorities authorities) {
        return ResponseEntity.ok(this.authoritiesService.save(authorities));
    }


    /**
     * 修改用户权限
     *
     * @param authorities 用户权限
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户权限", notes = "修改用户权限")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Authorities authorities) {
        return ResponseEntity.ok(this.authoritiesService.updateById(authorities));
    }


    /**
     * 通过id删除用户权限
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除用户权限", notes = "通过id删除用户权限")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.authoritiesService.removeById(id));
    }

}