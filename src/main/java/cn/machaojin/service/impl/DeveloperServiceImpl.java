package cn.machaojin.service.impl;

import cn.machaojin.domain.Developer;
import cn.machaojin.mapper.DeveloperMapper;
import cn.machaojin.service.DeveloperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author machaojin
* @description 针对表【developer(开发人员表)】的数据库操作Service实现
* @createDate 2024-04-29 17:30:59
*/
@Service
public class DeveloperServiceImpl extends ServiceImpl<DeveloperMapper, Developer>
    implements DeveloperService {

}




