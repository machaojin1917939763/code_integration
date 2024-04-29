package cn.machaojin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 问题表(Issue)表实体类
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Issue extends Model<Issue> {

    private Integer id;
    //问题类型
    private String issueType;
    //问题描述
    private String description;
    //问题解决人
    private String resolver;
    //问题分数
    private Integer score;
    //是否解决
    private Integer isResolved;
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

