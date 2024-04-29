package cn.machaojin.service.impl;

import cn.machaojin.domain.Project;
import cn.machaojin.mapper.ProjectMapper;
import cn.machaojin.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author machaojin
* @description 针对表【project(项目表)】的数据库操作Service实现
* @createDate 2024-04-29 17:30:59
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService {

}




