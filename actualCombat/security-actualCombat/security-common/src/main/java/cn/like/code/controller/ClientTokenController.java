package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.ClientToken;
import cn.like.code.service.ClientTokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 客户token表(ClientToken)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:19
 */
@RestController
@RequestMapping("clientToken")
@RequiredArgsConstructor
public class ClientTokenController extends BaseController {
    /**
     * 服务对象
     */
    private final ClientTokenService clientTokenService;


    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param clientToken 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getClientTokenPage(Page<ClientToken> page, ClientToken clientToken) {
        return ResponseEntity.ok((this.clientTokenService.page(page, new QueryWrapper<>(clientToken))));
    }


    /**
     * 通过id查询客户token表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientTokenService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param clientToken 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增客户token表", notes = "新增客户token表")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ClientToken clientToken) {
        return ResponseEntity.ok(this.clientTokenService.save(clientToken));
    }


    /**
     * 修改客户token表
     *
     * @param clientToken 客户token表
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改客户token表", notes = "修改客户token表")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody ClientToken clientToken) {
        return ResponseEntity.ok(this.clientTokenService.updateById(clientToken));
    }


    /**
     * 通过id删除客户token表
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除客户token表", notes = "通过id删除客户token表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientTokenService.removeById(id));
    }

}