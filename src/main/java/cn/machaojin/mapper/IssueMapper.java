package cn.machaojin.mapper;

import cn.machaojin.domain.Issue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author machaojin
* @description 针对表【issue(问题表)】的数据库操作Mapper
* @createDate 2024-04-29 17:30:59
* @Entity generator.domain.Issue
*/
@Mapper
public interface IssueMapper extends BaseMapper<Issue> {

}




