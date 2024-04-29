package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.ProjectIssueRelationDao;
import cn.machaojin.entity.ProjectIssueRelation;
import cn.machaojin.service.ProjectIssueRelationService;
import org.springframework.stereotype.Service;

/**
 * 项目问题关系表(ProjectIssueRelation)表服务实现类
 *
 * @author makejava
 * @since 2024-04-29 16:25:09
 */
@Service("projectIssueRelationService")
public class ProjectIssueRelationServiceImpl extends ServiceImpl<ProjectIssueRelationDao, ProjectIssueRelation> implements ProjectIssueRelationService {

}

