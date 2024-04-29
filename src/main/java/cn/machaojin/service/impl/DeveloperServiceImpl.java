package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.DeveloperDao;
import cn.machaojin.entity.Developer;
import cn.machaojin.service.DeveloperService;
import org.springframework.stereotype.Service;

/**
 * 开发人员表(Developer)表服务实现类
 *
 * @author makejava
 * @since 2024-04-29 16:25:07
 */
@Service("developerService")
public class DeveloperServiceImpl extends ServiceImpl<DeveloperDao, Developer> implements DeveloperService {

}

