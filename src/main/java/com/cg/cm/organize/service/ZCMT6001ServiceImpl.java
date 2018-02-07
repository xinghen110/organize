package com.cg.cm.organize.service;

import com.alibaba.fastjson.JSON;
import com.cg.cm.organize.dao.ZCMT6001Dao;
import com.cg.cm.organize.entity.ZCMT6001;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ZCMT6001ServiceImpl.class);

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
        try{
            if(Strings.isNullOrEmpty(zcmt6001.getDpnum())){
                String maxDpnum = dao.getMaxDpnum(zcmt6001.getBukrs());
                if(Strings.isNullOrEmpty(maxDpnum)){
                    maxDpnum = zcmt6001.getBukrs()+"0000";
                }
                int max = Integer.parseInt(maxDpnum.substring(4,7))+1;
                String dpnum = zcmt6001.getBukrs()+Strings.padStart(Integer.toString(max),4, '0');
                logger.info("公司代码{}当前最大的编号为{}，生成新部门的编号为{}。", zcmt6001.getBukrs(), maxDpnum, dpnum);
                zcmt6001.setDpnum(dpnum);
            }
            logger.info("保存部门数据：{}", zcmt6001.toJson());
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
            logger.warn("删除部门数据：{}", zcmt6001.toJson());
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
            Page<ZCMT6001> page =  dao.findAllByBukrsOrderBySequeAsc(bukrs, pageable);
            logger.info("部门列表：{}", JSON.toJSONString(page));
            return page;
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
            List<ZCMT6001> list =  dao.findAllByBukrsOrderBySequeAsc(bukrs);
            logger.info("部门列表：{}", JSON.toJSONString(list));
            return list;
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
            ZCMT6001 depart = dao.findByDpnum(dpnum);
            logger.info("部门：{}", JSON.toJSONString(depart));
            return depart;
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
            List<ZCMT6001> list = dao.findAllByDpnamContainingIgnoringCase(dpnam);
            logger.info("部门：{}", JSON.toJSONString(list));
            return list;
        } catch (Exception e) {
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 检查部门代码是否为空
     *
     * @param dpnum 部门代码
     */
    @Override
    public void checkDpnumIsNull(String dpnum) throws CMException {
        if (Strings.isNullOrEmpty(dpnum)) {
            throw new CMException("部门代码不能为空！");
        }
    }

}
