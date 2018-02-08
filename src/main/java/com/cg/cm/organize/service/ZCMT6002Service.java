package com.cg.cm.organize.service;


import com.cg.cm.organize.entity.ZCMT6002;
import com.demo.cm.utils.CMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by hexin on 2018/2/8.
 */
public interface ZCMT6002Service {
    /**
     * 保存岗位
     * */
    ZCMT6002 saveZCMT6002(ZCMT6002 zcmt6002) throws CMException;

    /**
     * 删除岗位
     * */
    void deleteZCMT6002(ZCMT6002 zcmt6002) throws CMException;

    /**
     * 分页显示部门下所有岗位
     * */
    Page<ZCMT6002> pageAll(String dpnum, Pageable pageable) throws CMException;
    /**
     * 显示部门下所有岗位（不分页）
     * */
    List<ZCMT6002> getAll(String dpnum) throws CMException;

    /**
     * 显示公司下所有岗位（不分页）
     * */
    List<ZCMT6002> getAllByBukrs(String bukrs) throws CMException;
    /**
     * 根据岗位代码返回岗位
     * */
    ZCMT6002 getGWNUM(String gwnum) throws CMException;
    /**
     * 根据部门代码、岗位名称查询岗位
     * */
    List<ZCMT6002> getGWNAM(String dpnum, String gwnam) throws CMException;
    /**
     * 检查岗位代码是否为空
     */
    void checkGwnumIsNull(String gwnum) throws CMException;
    /**
     * 检查外键是否存在
     */
    void checkForeignExist(String gwnum) throws CMException;
}
