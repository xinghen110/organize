package com.cg.cm.organize.service;

import com.alibaba.fastjson.JSON;
import com.cg.cm.organize.dao.ZCMT6002Dao;
import com.cg.cm.organize.entity.ZCMT6002;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hexin on 2018/2/8.
 *
 */
@Component("ZCMT6002Service")
public class ZCMT6002ServiceImpl implements ZCMT6002Service {

    private static final Logger logger = LoggerFactory.getLogger(ZCMT6002ServiceImpl.class);

    private final ZCMT6002Dao dao;
    private final ZCMT6000Service zcmt6000Service;
    private final ZCMT6001Service zcmt6001Service;

    public ZCMT6002ServiceImpl(ZCMT6002Dao dao, ZCMT6001Service zcmt6001Service, ZCMT6000Service zcmt6000Service) {
        this.dao = dao;
        this.zcmt6001Service = zcmt6001Service;
        this.zcmt6000Service = zcmt6000Service;
    }

    /**
     * 保存岗位
     *
     * @param zcmt6002 岗位
     */
    @Override
    public ZCMT6002 saveZCMT6002(ZCMT6002 zcmt6002) throws CMException {
        zcmt6001Service.checkDpnumIsNull(zcmt6002.getDpnum());
        zcmt6001Service.checkForeignExist(zcmt6002.getDpnum());
        try{
            if(Strings.isNullOrEmpty(zcmt6002.getGwnum())){
                String maxGwnum = dao.getMaxGwnum(zcmt6002.getDpnum());
                if(Strings.isNullOrEmpty(maxGwnum)){
                    maxGwnum = zcmt6002.getDpnum()+"0000";
                }
                int max = Integer.parseInt(maxGwnum.substring(8,12))+1;
                String gwnum = zcmt6002.getDpnum()+Strings.padStart(Integer.toString(max),4, '0');
                logger.info("公司代码{}，部门代码{}当前最大的编号为{}，生成新岗位的编号为{}。",
                        zcmt6002.getBukrs(), zcmt6002.getDpnum(), maxGwnum, gwnum);
                zcmt6002.setGwnum(gwnum);
            }
            logger.info("保存岗位数据：{}", zcmt6002.toJson());
            return dao.save(zcmt6002);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 删除岗位
     *
     * @param zcmt6002 岗位
     */
    @Override
    public void deleteZCMT6002(ZCMT6002 zcmt6002) throws CMException {
        checkGwnumIsNull(zcmt6002.getGwnum());
        try{
            logger.warn("删除岗位数据：{}", zcmt6002.toJson());
            dao.delete(zcmt6002);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 分页显示部门下所有岗位
     *
     * @param dpnum  所属部门代码
     * @param pageable  分页参数
     */
    @Override
    public Page<ZCMT6002> pageAll(String dpnum, Pageable pageable) throws CMException {
        zcmt6001Service.checkDpnumIsNull(dpnum);
        try{
            Page<ZCMT6002> page =  dao.findAllByDpnum(dpnum, pageable);
            logger.info("岗位列表：{}", JSON.toJSONString(page));
            return page;
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 显示部门下所有岗位（不分页）
     *
     * @param dpnum 部门代码
     */
    @Override
    public List<ZCMT6002> getAll(String dpnum) throws CMException {
        zcmt6001Service.checkDpnumIsNull(dpnum);
        try{
            List<ZCMT6002> list =  dao.findAllByDpnum(dpnum);
            logger.info("岗位列表：{}", JSON.toJSONString(list));
            return list;
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 显示公司下所有岗位（不分页）
     *
     * @param bukrs
     */
    @Override
    public List<ZCMT6002> getAllByBukrs(String bukrs) throws CMException {
        zcmt6000Service.checkBukrsIsNull(bukrs);
        try{
            List<ZCMT6002> list =  dao.findAllByBukrs(bukrs);
            logger.info("岗位列表：{}", JSON.toJSONString(list));
            return list;
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据岗位代码返回岗位
     *
     * @param gwnum 岗位代码
     */
    @Override
    public ZCMT6002 getGWNUM(String gwnum) throws CMException {
        checkGwnumIsNull(gwnum);
        try{
            ZCMT6002 depart = dao.findByGwnum(gwnum);
            logger.info("岗位：{}", JSON.toJSONString(depart));
            return depart;
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据岗位名称查询岗位
     *
     * @param dpnum     部门代码
     * @param gwnam     岗位名称
     */
    @Override
    public List<ZCMT6002> getGWNAM(String dpnum, String gwnam) throws CMException {
        try {
            List<ZCMT6002> list = dao.findAllByDpnumAndGwnamContainingIgnoringCase(dpnum, gwnam);
            logger.info("岗位：{}", JSON.toJSONString(list));
            return list;
        } catch (Exception e) {
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 检查岗位代码是否为空
     *
     * @param gwnum 岗位代码
     */
    @Override
    public void checkGwnumIsNull(String gwnum) throws CMException {
        if (Strings.isNullOrEmpty(gwnum)) {
            throw new CMException("岗位代码不能为空！");
        }
    }

    /**
     * 检查外键是否存在
     *
     * @param gwnum 岗位代码
     */
    @Override
    public void checkForeignExist(String gwnum) throws CMException {
        if(!Strings.isNullOrEmpty(gwnum)){
            if(this.getGWNUM(gwnum) == null){
                throw new CMException("岗位代码不存在！");
            }
        }
    }

}
