package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Code;
import cn.like.code.service.CodeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 授权码(Code)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:21
 */
@RestController
@RequestMapping("code")
@RequiredArgsConstructor
public class CodeController extends BaseController {
    /**
     * 服务对象
     */
    private final CodeService codeService;


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param code 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getCodePage(Page<Code> page, Code code) {
        return ResponseEntity.ok((this.codeService.page(page, new QueryWrapper<>(code))));
    }


    /**
     * 通过id查询授权码
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.codeService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param code 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增授权码", notes = "新增授权码")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Code code) {
        return ResponseEntity.ok(this.codeService.save(code));
    }


    /**
     * 修改授权码
     *
     * @param code 授权码
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改授权码", notes = "修改授权码")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Code code) {
        return ResponseEntity.ok(this.codeService.updateById(code));
    }


    /**
     * 通过id删除授权码
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除授权码", notes = "通过id删除授权码")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.codeService.removeById(id));
    }

}