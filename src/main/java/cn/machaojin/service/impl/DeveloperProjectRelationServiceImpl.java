package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.DeveloperProjectRelationDao;
import cn.machaojin.entity.DeveloperProjectRelation;
import cn.machaojin.service.DeveloperProjectRelationService;
import org.springframework.stereotype.Service;

/**
 * 开发人员项目关系表(DeveloperProjectRelation)表服务实现类
 *
 * @author makejava
 * @since 2024-04-29 16:25:08
 */
@Service("developerProjectRelationService")
public class DeveloperProjectRelationServiceImpl extends ServiceImpl<DeveloperProjectRelationDao, DeveloperProjectRelation> implements DeveloperProjectRelationService {

}

