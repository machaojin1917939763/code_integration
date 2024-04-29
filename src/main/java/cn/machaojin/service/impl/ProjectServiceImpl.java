package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.ProjectDao;
import cn.machaojin.entity.Project;
import cn.machaojin.service.ProjectService;
import org.springframework.stereotype.Service;

/**
 * 项目表(Project)表服务实现类
 *
 * @author makejava
 * @since 2024-04-29 16:25:09
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, Project> implements ProjectService {

}

