package com.cg.cm.organize.service;


import com.cg.cm.organize.entity.ZCMT6001;
import com.demo.cm.utils.CMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by hexin on 2018/2/6.
 */
public interface ZCMT6001Service {
    /**
     * 保存部门
     * */
    ZCMT6001 saveZCMT6001(ZCMT6001 zcmt6001) throws CMException;

    /**
     * 删除部门
     * */
    void deleteZCMT6001(ZCMT6001 zcmt6001) throws CMException;

    /**
     * 分页显示公司下所有部门
     * */
    Page<ZCMT6001> pageAll(String bukrs, Pageable pageable) throws CMException;
    /**
     * 显示公司下所有部门（不分页）
     * */
    List<ZCMT6001> getAll(String bukrs) throws CMException;
    /**
     * 根据部门代码返回部门
     * */
    ZCMT6001 getDPNUM(String dpnum) throws CMException;
    /**
     * 根据部门名称查询部门
     * */
    List<ZCMT6001> getDPNAM(String dpnam) throws CMException;
    /**
     * 检查部门代码是否为空
     */
    void checkDpnumIsNull(String dpnum) throws CMException;
}
