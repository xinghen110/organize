package com.cg.cm.organize.service;

import com.cg.cm.organize.dao.ZCMT6000Dao;
import com.cg.cm.organize.entity.ZCMT6000;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hexin on 2018/1/24.
 *
 */
@Component("ZCMT6000Service")
public class ZCMT6000ServiceImpl implements ZCMT6000Service {

    private final ZCMT6000Dao dao;

    public ZCMT6000ServiceImpl(ZCMT6000Dao dao) {
        this.dao = dao;
    }
    /**
     * 保存公司
     *
     * @param zcmt6000
     */
    @Override
    public ZCMT6000 saveZCMT6000(ZCMT6000 zcmt6000) throws CMException{
        checkBukrsIsNull(zcmt6000.getBukrs());
        checkForeignExist(zcmt6000.getBukrp());
        try{
            return dao.save(zcmt6000);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 删除公司
     *
     * @param zcmt6000
     */
    @Override
    public void deleteZCMT6000(ZCMT6000 zcmt6000) throws CMException{
        checkBukrsIsNull(zcmt6000.getBukrs());
        try{
            dao.delete(zcmt6000);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 分页显示所有公司
     *
     * @param pageable
     */
    @Override
    public Page<ZCMT6000> getAll(Pageable pageable) throws CMException {
        try{
            return dao.findAll(pageable);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }
    /**
     * 分页显示所有子公司
     *
     * @param bukrs
     * @param pageable
     */
    @Override
    public Page<ZCMT6000> getAllChild(String bukrs, Pageable pageable) throws CMException {
        try{
            return dao.findAllByBukrp(bukrs, pageable);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 获取所有公司（不分页）
     */
    @Override
    public List<ZCMT6000> getAll() throws CMException {
        try{
            return dao.findAll();
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据公司代码返回公司
     *
     * @param bukrs
     */
    @Override
    public ZCMT6000 getBUKRS(String bukrs) throws CMException {
        checkBukrsIsNull(bukrs);
        try{
            return dao.findByBukrs(bukrs);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据公司名称查询公司
     *
     * @param pageable
     */
    @Override
    public Page<ZCMT6000> getBUNAM(String buname, Pageable pageable) throws CMException {
        try{
            return dao.findAllByBunamContainingIgnoringCase(buname, pageable);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 根据公司代码查找所有子公司
     *
     * @param bukrs
     */
    @Override
    public List<ZCMT6000> getChild(String bukrs) throws CMException {
        checkBukrsIsNull(bukrs);
        try{
            return dao.findAllByBukrp(bukrs);
        }catch (Exception e){
            throw new CMException(e.getMessage());
        }
    }

    /**
     * 检查公司代码是否为空
     *
     * @param bukrs
     */
    @Override
    public void checkBukrsIsNull(String bukrs) throws CMException {
        if(Strings.isNullOrEmpty(bukrs)){
            throw new CMException("公司代码不能为空！");
        }
    }

    /**
     * 检查外键是否存在
     *
     * @param bukrs 待检查数据
     */
    @Override
    public void checkForeignExist(String bukrs) throws CMException {
        if(!Strings.isNullOrEmpty(bukrs)){
            if(this.getBUKRS(bukrs) == null){
                throw new CMException("上级公司代码不存在！");
            }
        }
    }
}
