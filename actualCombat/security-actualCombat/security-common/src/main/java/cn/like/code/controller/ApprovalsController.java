package cn.like.code.controller;


import cn.like.code.base.BaseController;
import cn.like.code.entity.Approvals;
import cn.like.code.service.ApprovalsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;


/**
 * 批准的用户(Approvals)表控制层
 *
 * @author like
 * @since 2021-06-04 09:09:13
 */
@RestController
@RequestMapping("approvals")
@RequiredArgsConstructor
public class ApprovalsController extends BaseController {
    /**
     * 服务对象
     */
    private final ApprovalsService approvalsService;


    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param approvals 查询实体
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public ResponseEntity<?> getApprovalsPage(Page<Approvals> page, Approvals approvals) {
        return ResponseEntity.ok((this.approvalsService.page(page, new QueryWrapper<>(approvals))));
    }


    /**
     * 通过id查询批准的用户
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.approvalsService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param approvals 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增批准的用户", notes = "新增批准的用户")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Approvals approvals) {
        return ResponseEntity.ok(this.approvalsService.save(approvals));
    }


    /**
     * 修改批准的用户
     *
     * @param approvals 批准的用户
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改批准的用户", notes = "修改批准的用户")
    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody Approvals approvals) {
        return ResponseEntity.ok(this.approvalsService.updateById(approvals));
    }


    /**
     * 通过id删除批准的用户
     *
     * @param id id
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过id删除批准的用户", notes = "通过id删除批准的用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Serializable id) {
        return ResponseEntity.ok(this.approvalsService.removeById(id));
    }

}