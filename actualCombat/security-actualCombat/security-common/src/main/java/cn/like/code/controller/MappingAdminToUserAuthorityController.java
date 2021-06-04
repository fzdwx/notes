package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.MappingAdminToUserAuthority;
import cn.like.code.service.MappingAdminToUserAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 用户和用户职权的映射表.(MappingAdminToUserAuthority)表控制层
 *
 * @author like
 * @since 2021-06-04 13:13:59
 */
@RestController
@RequestMapping("mappingAdminToUserAuthority")
@RequiredArgsConstructor
public class MappingAdminToUserAuthorityController extends BaseController {
    /**
     * 服务对象
     */
    private final MappingAdminToUserAuthorityService mappingAdminToUserAuthorityService;


    /**
     * 分页查询所有数据
     *
     * @param page                        分页对象
     * @param mappingAdminToUserAuthority 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getMappingAdminToUserAuthorityPage(Page<MappingAdminToUserAuthority> page, MappingAdminToUserAuthority mappingAdminToUserAuthority) {
        return ResponseEntity.ok((this.mappingAdminToUserAuthorityService
                .page(page, new QueryWrapper<>(mappingAdminToUserAuthority))));
    }


    /**
     * 通过id查询用户和用户职权的映射表.
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingAdminToUserAuthorityService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param mappingAdminToUserAuthority 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增用户和用户职权的映射表.", notes = "新增用户和用户职权的映射表.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody MappingAdminToUserAuthority mappingAdminToUserAuthority) {
        return ResponseEntity.ok(this.mappingAdminToUserAuthorityService.save(mappingAdminToUserAuthority));
    }


    /**
     * 修改用户和用户职权的映射表.
     *
     * @param mappingAdminToUserAuthority 用户和用户职权的映射表.
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户和用户职权的映射表.", notes = "修改用户和用户职权的映射表.")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody MappingAdminToUserAuthority mappingAdminToUserAuthority) {
        return ResponseEntity.ok(this.mappingAdminToUserAuthorityService.updateById(mappingAdminToUserAuthority));
    }


    /**
     * 通过id删除用户和用户职权的映射表.
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除用户和用户职权的映射表.", notes = "通过id删除用户和用户职权的映射表.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.mappingAdminToUserAuthorityService.removeById(id));
    }

}