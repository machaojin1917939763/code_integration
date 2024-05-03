package cn.machaojin.controller;


import cn.machaojin.controller.baseController.ApiController;
import cn.machaojin.domain.Issue;
import cn.machaojin.service.IssueService;
import cn.machaojin.tool.ApiResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

import static cn.machaojin.tool.ApiResult.success;

/**
 * 问题表(Issue)表控制层
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:08
 */
@RestController
@RequestMapping("/issue")
public class IssueController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private IssueService issueService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param issue 查询实体
     * @return 所有数据
     */
    @PostMapping("/getList")
    public ApiResult selectAll(@RequestParam(value = "current", defaultValue = "1") int current,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               @RequestBody Issue issue) {
        Page<Issue> page = new Page<>(current, pageSize);
        return success(this.issueService.page(page, new QueryWrapper<>(issue)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResult selectOne(@PathVariable Serializable id) {
        return success(this.issueService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param issue 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResult insert(@RequestBody Issue issue) {
        return success(this.issueService.save(issue));
    }

    /**
     * 修改数据
     *
     * @param issue 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ApiResult update(@RequestBody Issue issue) {
        return success(this.issueService.updateById(issue));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResult delete(@RequestParam("idList") List<Long> idList) {
        return success(this.issueService.removeByIds(idList));
    }
}

