package com.cg.cm.organize.entity;

import com.alibaba.fastjson.JSON;
import com.demo.cm.utils.BaseEntity;
import com.demo.cm.utils.CMException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hexin on 2018/2/6.
 *
 * 合同管理系统-部门及其属性
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZCMT6001 extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门代码
     * */
    @Id
    @Column(nullable = false, length = 8, unique = true, name = "DPNUM")
    private String dpnum;
    /**
     * 公司代码
     * */
    @Column(unique = false, name = "BUKRS")
    private String bukrs;

    /**
     * 排列顺序
     * */
    @Column(nullable = false, name = "SEQUE")
    private int seque;

    /**
     * 部门名称
     * */
    @Column(nullable = true, length = 25, name = "DPNAM")
    private String dpnam;

    /**
     * 部门描述
     * */
    @Column(nullable = true, length = 40, name = "DPTXT")
    private String dptxt;


    public String toJson()throws CMException {
        return JSON.toJSONString(this);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getDpnam() {
        return dpnam;
    }

    public void setDpnam(String dpnam) {
        this.dpnam = dpnam;
    }

    public String getDptxt() {
        return dptxt;
    }

    public void setDptxt(String dptxt) {
        this.dptxt = dptxt;
    }
}
