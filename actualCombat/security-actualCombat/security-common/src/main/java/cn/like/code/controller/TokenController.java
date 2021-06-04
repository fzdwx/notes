package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Token;
import cn.like.code.service.TokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 访问令牌(Token)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:23
 */
@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController extends BaseController {
    /**
     * 服务对象
     */
    private final TokenService tokenService;


    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param token 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getTokenPage(Page<Token> page, Token token) {
        return ResponseEntity.ok((this.tokenService.page(page, new QueryWrapper<>(token))));
    }


    /**
     * 通过id查询访问令牌
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.tokenService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param token 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增访问令牌", notes = "新增访问令牌")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Token token) {
        return ResponseEntity.ok(this.tokenService.save(token));
    }


    /**
     * 修改访问令牌
     *
     * @param token 访问令牌
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改访问令牌", notes = "修改访问令牌")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Token token) {
        return ResponseEntity.ok(this.tokenService.updateById(token));
    }


    /**
     * 通过id删除访问令牌
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除访问令牌", notes = "通过id删除访问令牌")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.tokenService.removeById(id));
    }

}