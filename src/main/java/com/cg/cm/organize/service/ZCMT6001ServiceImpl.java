package com.cg.cm.organize.service;

import com.cg.cm.organize.dao.ZCMT6001Dao;
import com.cg.cm.organize.entity.ZCMT6001;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hexin on 2018/2/6.
 *
 */
@Component("ZCMT6001Service")
public class ZCMT6001ServiceImpl implements ZCMT6001Service {

    private final ZCMT6001Dao dao;
    private final ZCMT6000Service zcmt6000Service;

    public ZCMT6001ServiceImpl(ZCMT6001Dao dao, ZCMT6000Service zcmt6000Service) {
        this.dao = dao;
        this.zcmt6000Service = zcmt6000Service;
    }

    /**
     * 保存部门
     *
     * @param zcmt6001 部门
     */
    @Override
    public ZCMT6001 saveZCMT6001(ZCMT6001 zcmt6001) throws CMException {
        zcmt6000Service.checkBukrsIsNull(zcmt6001.getBukrs());
        zcmt6000Service.checkForeignExist(zcmt6001.getBukrs());
        checkDpnumIsNull(zcmt6001.getDpnum());
        try{
            return dao.save(zcmt6001);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 删除部门
     *
     * @param zcmt6001 部门
     */
    @Override
    public void deleteZCMT6001(ZCMT6001 zcmt6001) throws CMException {
        checkDpnumIsNull(zcmt6001.getDpnum());
        try{
            dao.delete(zcmt6001);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 分页显示公司下所有部门
     *
     * @param bukrs  所属公司代码
     * @param pageable  分页参数
     */
    @Override
    public Page<ZCMT6001> getAll(String bukrs, Pageable pageable) throws CMException {
        zcmt6000Service.checkBukrsIsNull(bukrs);
        try{
            return dao.findAllByBukrsOrderBySequeAsc(bukrs, pageable);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 显示公司下所有部门（不分页）
     *
     * @param bukrs 公司代码
     */
    @Override
    public List<ZCMT6001> getAll(String bukrs) throws CMException {
        zcmt6000Service.checkBukrsIsNull(bukrs);
        try{
            return dao.findAllByBukrsOrderBySequeAsc(bukrs);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据部门代码返回部门
     *
     * @param dpnum 部门代码
     */
    @Override
    public ZCMT6001 getDPNUM(String dpnum) throws CMException {
        checkDpnumIsNull(dpnum);
        try{
            return dao.findByDpnum(dpnum);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据部门名称查询部门
     *
     * @param dpnam     部门名称
     */
    @Override
    public List<ZCMT6001> getDPNAM(String dpnam) throws CMException {
        checkDpnumIsNull(dpnam);
        try {
            return dao.findAllByDpnamContainingIgnoringCase(dpnam);
        } catch (Exception e) {
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 检查部门代码是否为空
     *
     * @param dpnum
     */
    @Override
    public void checkDpnumIsNull(String dpnum) throws CMException {
        if (Strings.isNullOrEmpty(dpnum)) {
            throw new CMException("部门代码不能为空！");
        }
    }

}
