package cn.machaojin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.machaojin.dao.IssueDao;
import cn.machaojin.entity.Issue;
import cn.machaojin.service.IssueService;
import org.springframework.stereotype.Service;

/**
 * 问题表(Issue)表服务实现类
 *
 * @author makejava
 * @since 2024-04-29 16:25:08
 */
@Service("issueService")
public class IssueServiceImpl extends ServiceImpl<IssueDao, Issue> implements IssueService {

}

