package com.cg.cm.organize.entity;

import com.alibaba.fastjson.JSON;
import com.demo.cm.utils.BaseEntity;
import com.demo.cm.utils.CMException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by hexin on 2018/1/15.
 *
 * 合同管理系统-公司及其属性
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZCMT6000 extends BaseEntity implements Serializable {

    private final String type = "bukrs";
    private static final long serialVersionUID = 1L;

    /**
     * 公司代码
     * */
    @Id
    @Column(nullable = false, length = 4, unique = true, name = "BUKRS")
    private String bukrs;

    /**
     * 排列顺序
     * */
    @Column(nullable = false, name = "SEQUE")
    private int seque;

    /**
     * 所属公司
     * */
    @Column(nullable = true, length = 4, name = "BUKRP")
    private String bukrp;

    /**
     * 公司名称
     * */
    @Column(nullable = true, length = 25, name = "BUNAM")
    private String bunam;

    /**
     * 公司描述
     * */
    @Column(nullable = true, length = 40, name = "BUTXT")
    private String butxt;


    public String toJson()throws CMException {
        return JSON.toJSONString(this);
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

    public String getBukrp() {
        return bukrp;
    }

    public void setBukrp(String bukrp) {
        this.bukrp = bukrp;
    }

    public String getBunam() {
        return bunam;
    }

    public void setBunam(String bunam) {
        this.bunam = bunam;
    }

    public String getButxt() {
        return butxt;
    }

    public void setButxt(String butxt) {
        this.butxt = butxt;
    }

    public String getType() {
        return type;
    }
}
