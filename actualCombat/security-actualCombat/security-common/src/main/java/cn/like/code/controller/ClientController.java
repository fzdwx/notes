package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Client;
import cn.like.code.service.ClientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 客户端(Client)表控制层
 *
 * @author like
 * @since 2021-06-04 13:13:53
 */
@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController extends BaseController {
    /**
     * 服务对象
     */
    private final ClientService clientService;


    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param client 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getClientPage(Page<Client> page, Client client) {
        return ResponseEntity.ok((this.clientService.page(page, new QueryWrapper<>(client))));
    }


    /**
     * 通过id查询客户端
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param client 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增客户端", notes = "新增客户端")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Client client) {
        return ResponseEntity.ok(this.clientService.save(client));
    }


    /**
     * 修改客户端
     *
     * @param client 客户端
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改客户端", notes = "修改客户端")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Client client) {
        return ResponseEntity.ok(this.clientService.updateById(client));
    }


    /**
     * 通过id删除客户端
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除客户端", notes = "通过id删除客户端")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.clientService.removeById(id));
    }

}