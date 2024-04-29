package cn.machaojin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 问题表(Issue)表实体类
 *
 * @author makejava
 * @since 2024-04-29 16:25:08
 */
@SuppressWarnings("serial")
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(Integer isResolved) {
        this.isResolved = isResolved;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

