package cn.machaojin.mapper;

import cn.machaojin.domain.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author machaojin
* @description 针对表【project(项目表)】的数据库操作Mapper
* @createDate 2024-04-29 17:30:59
* @Entity generator.domain.Project
*/
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

}




