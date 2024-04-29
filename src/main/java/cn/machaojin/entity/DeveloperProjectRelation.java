package cn.machaojin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 开发人员项目关系表(DeveloperProjectRelation)表实体类
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeveloperProjectRelation extends Model<DeveloperProjectRelation> {

    private Integer id;
    //开发人员ID
    private Integer developerId;
    //项目ID
    private Integer projectId;
    //创建者
    private String creator;
    //创建时间
    private Date createdAt;
    //更新者
    private String updater;
    //更新时间
    private Date updatedAt;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

