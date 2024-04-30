package cn.machaojin.service.impl;

import cn.machaojin.domain.Issue;
import cn.machaojin.mapper.IssueMapper;
import cn.machaojin.service.IssueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author machaojin
* @description 针对表【issue(问题表)】的数据库操作Service实现
* @createDate 2024-04-29 17:30:59
*/
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue>
    implements IssueService {

}




