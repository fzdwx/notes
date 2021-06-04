package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.ClientDetails;
import cn.like.code.service.ClientDetailsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 认证客户端详情表(ClientDetails)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:17
 */
@RestController
@RequestMapping("clientDetails")
@RequiredArgsConstructor
public class ClientDetailsController extends BaseController {
    /**
     * 服务对象
     */
    private final ClientDetailsService clientDetailsService;


    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param clientDetails 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getClientDetailsPage(Page<ClientDetails> page, ClientDetails clientDetails) {
        return ResponseEntity.ok((this.clientDetailsService.page(page, new QueryWrapper<>(clientDetails))));
    }


    /**
     * 通过id查询认证客户端详情表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientDetailsService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param clientDetails 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增认证客户端详情表", notes = "新增认证客户端详情表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ClientDetails clientDetails) {
        return ResponseEntity.ok(this.clientDetailsService.save(clientDetails));
    }


    /**
     * 修改认证客户端详情表
     *
     * @param clientDetails 认证客户端详情表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改认证客户端详情表", notes = "修改认证客户端详情表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody ClientDetails clientDetails) {
        return ResponseEntity.ok(this.clientDetailsService.updateById(clientDetails));
    }


    /**
     * 通过id删除认证客户端详情表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除认证客户端详情表", notes = "通过id删除认证客户端详情表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientDetailsService.removeById(id));
    }

}