package com.cg.cm.organize.entity;

import com.alibaba.fastjson.JSON;
import com.demo.cm.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by hexin on 2018/2/8.
 *
 * 合同管理系统-岗位及其属性
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZCMT6002 extends BaseEntity implements Serializable {

    private final String type = "gangwei";

    private static final long serialVersionUID = 1L;
    /**
     * 岗位代码
     * */
    @Id
    @Column(nullable = false, length = 12, unique = true, name = "GWNUM")
    private String gwnum;
    /**
     * 部门代码
     * */
    @Column(nullable = false, length = 8, name = "DPNUM")
    private String dpnum;
    /**
     * 公司代码
     * */
    @Column(nullable = false, length = 4, name = "BUKRS")
    private String bukrs;

    /**
     * 排列顺序
     * */
    @Column(nullable = false, name = "SEQUE")
    private int seque;

    /**
     * 岗位名称
     * */
    @Column(length = 25, name = "GWNAM")
    private String gwnam;

    /**
     * 岗位描述
     * */
    @Column(length = 40, name = "GWTXT")
    private String gwtxt;


    public String toJson(){
        return JSON.toJSONString(this);
    }

    public String getDpnum() {
        return dpnum;
    }

    public void setDpnum(String dpnum) {
        this.dpnum = dpnum;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public int getSeque() {
        return seque;
    }

    public void setSeque(int seque) {
        this.seque = seque;
    }

    public String getGwnum() {
        return gwnum;
    }

    public void setGwnum(String gwnum) {
        this.gwnum = gwnum;
    }

    public String getGwnam() {
        return gwnam;
    }

    public void setGwnam(String gwnam) {
        this.gwnam = gwnam;
    }

    public String getGwtxt() {
        return gwtxt;
    }

    public void setGwtxt(String gwtxt) {
        this.gwtxt = gwtxt;
    }

    public String getType() {
        return type;
    }
}
