package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.AdminAuthority;
import cn.like.code.service.AdminAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).(AdminAuthority)表控制层
 *
 * @author like
 * @since 2021-06-04 13:13:50
 */
@RestController
@RequestMapping("adminAuthority")
@RequiredArgsConstructor
public class AdminAuthorityController extends BaseController {
    /**
     * 服务对象
     */
    private final AdminAuthorityService adminAuthorityService;


    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param adminAuthority 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getAdminAuthorityPage(Page<AdminAuthority> page, AdminAuthority adminAuthority) {
        return ResponseEntity.ok((this.adminAuthorityService.page(page, new QueryWrapper<>(adminAuthority))));
    }


    /**
     * 通过id查询用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminAuthorityService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param adminAuthority 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "新增用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AdminAuthority adminAuthority) {
        return ResponseEntity.ok(this.adminAuthorityService.save(adminAuthority));
    }


    /**
     * 修改用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param adminAuthority 用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "修改用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody AdminAuthority adminAuthority) {
        return ResponseEntity.ok(this.adminAuthorityService.updateById(adminAuthority));
    }


    /**
     * 通过id删除用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "通过id删除用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.adminAuthorityService.removeById(id));
    }

}