package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.DeveloperIssueRelationDao;
import cn.machaojin.entity.DeveloperIssueRelation;
import cn.machaojin.service.DeveloperIssueRelationService;
import org.springframework.stereotype.Service;

/**
 * 开发人员问题关系表(DeveloperIssueRelation)表服务实现类
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:08
 */
@Service("developerIssueRelationService")
public class DeveloperIssueRelationServiceImpl extends ServiceImpl<DeveloperIssueRelationDao, DeveloperIssueRelation> implements DeveloperIssueRelationService {

}

