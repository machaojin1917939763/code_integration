package cn.machaojin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 开发人员表(Developer)表实体类
 *
 * @author Ma Chaojin
 * @since 2024-04-29 16:25:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Developer extends Model<Developer> {

    private Integer id;
    //开发人员姓名
    private String name;
    //性别
    private String gender;
    //部门
    private String department;
    //职位
    private String position;
    //电话
    private String phone;
    //邮件
    private String email;
    //项目ID
    private Integer projectId;
    //BUG ID
    private Integer bugId;
    //积分
    private Integer score;
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

