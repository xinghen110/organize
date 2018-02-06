package com.cg.cm.organize.service;


import com.cg.cm.organize.entity.ZCMT6000;
import com.demo.cm.utils.CMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by hexin on 2018/1/24.
 */
public interface ZCMT6000Service{
    /**
     * 保存公司
     * */
    ZCMT6000 saveZCMT6000(ZCMT6000 zcmt6000) throws CMException;

    /**
     * 删除公司
     * */
    void deleteZCMT6000(ZCMT6000 zcmt6000) throws CMException;

    /**
     * 分页显示所有公司
     * */
    Page<ZCMT6000> getAll(Pageable pageable) throws CMException;
    /**
     * 分页显示所有子公司
     * */
    Page<ZCMT6000> getAllChild(String bukrs, Pageable pageable) throws CMException;
    /**
     * 获取所有公司（不分页）
     * */
    List<ZCMT6000> getAll() throws CMException;
    /**
     * 根据公司代码返回公司
     * */
    ZCMT6000 getBUKRS(String bukrs) throws CMException;
    /**
     * 根据公司名称查询公司
     * */
    Page<ZCMT6000> getBUNAM(String bunam, Pageable pageable) throws CMException;
    /**
     * 根据公司代码查找所有子公司
     * */
    List<ZCMT6000> getChild(String bukrs) throws CMException;
    /**
     * 检查公司代码是否为空
     */
    void checkBukrsIsNull(String bukrs) throws CMException;
    /**
     * 检查外键是否存在
     */
    void checkForeignExist(String bukrs) throws CMException;
}
