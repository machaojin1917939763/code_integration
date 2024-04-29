package cn.machaojin.mapper;

import cn.machaojin.domain.Developer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author machaojin
* @description 针对表【developer(开发人员表)】的数据库操作Mapper
* @createDate 2024-04-29 17:30:59
* @Entity generator.domain.Developer
*/
@Mapper
public interface DeveloperMapper extends BaseMapper<Developer> {

}




