package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.ClientAuthority;
import cn.like.code.service.ClientAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).(ClientAuthority)表控制层
 *
 * @author like
 * @since 2021-06-04 13:13:55
 */
@RestController
@RequestMapping("clientAuthority")
@RequiredArgsConstructor
public class ClientAuthorityController extends BaseController {
    /**
     * 服务对象
     */
    private final ClientAuthorityService clientAuthorityService;


    /**
     * 分页查询所有数据
     *
     * @param page            分页对象
     * @param clientAuthority 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getClientAuthorityPage(Page<ClientAuthority> page, ClientAuthority clientAuthority) {
        return ResponseEntity.ok((this.clientAuthorityService.page(page, new QueryWrapper<>(clientAuthority))));
    }


    /**
     * 通过id查询客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientAuthorityService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param clientAuthority 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "新增客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ClientAuthority clientAuthority) {
        return ResponseEntity.ok(this.clientAuthorityService.save(clientAuthority));
    }


    /**
     * 修改客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param clientAuthority 客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "修改客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody ClientAuthority clientAuthority) {
        return ResponseEntity.ok(this.clientAuthorityService.updateById(clientAuthority));
    }


    /**
     * 通过id删除客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).", notes = "通过id删除客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientAuthorityService.removeById(id));
    }

}