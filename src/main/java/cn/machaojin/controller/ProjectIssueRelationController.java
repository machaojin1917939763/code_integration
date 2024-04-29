package cn.machaojin.controller;


import cn.machaojin.controller.baseController.ApiController;
import cn.machaojin.tool.ApiResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.machaojin.entity.ProjectIssueRelation;
import cn.machaojin.service.ProjectIssueRelationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static cn.machaojin.tool.ApiResult.success;

/**
 * 项目问题关系表(ProjectIssueRelation)表控制层
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:09
 */
@RestController
@RequestMapping("projectIssueRelation")
public class ProjectIssueRelationController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectIssueRelationService projectIssueRelationService;

    /**
     * 分页查询所有数据
     *
     * @param page                 分页对象
     * @param projectIssueRelation 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResult selectAll(Page<ProjectIssueRelation> page, ProjectIssueRelation projectIssueRelation) {
        return success(this.projectIssueRelationService.page(page, new QueryWrapper<>(projectIssueRelation)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResult selectOne(@PathVariable Serializable id) {
        return success(this.projectIssueRelationService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param projectIssueRelation 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResult insert(@RequestBody ProjectIssueRelation projectIssueRelation) {
        return success(this.projectIssueRelationService.save(projectIssueRelation));
    }

    /**
     * 修改数据
     *
     * @param projectIssueRelation 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ApiResult update(@RequestBody ProjectIssueRelation projectIssueRelation) {
        return success(this.projectIssueRelationService.updateById(projectIssueRelation));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResult delete(@RequestParam("idList") List<Long> idList) {
        return success(this.projectIssueRelationService.removeByIds(idList));
    }
}

