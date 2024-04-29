package cn.machaojin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目表(Project)表实体类
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Project extends Model<Project> {

    private Integer id;
    //项目名称
    private String name;
    //项目地址
    private String url;
    //项目类型
    private String type;
    //项目负责人
    private String responsiblePerson;
    //项目问题数
    private Integer issueCount;
    //问题ID
    private Integer issueId;
    //开发人员表ID
    private Integer developerId;
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

