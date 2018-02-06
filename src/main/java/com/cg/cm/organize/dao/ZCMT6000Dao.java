package com.cg.cm.organize.dao;

import com.cg.cm.organize.entity.ZCMT6000;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZCMT6000Dao extends CrudRepository<ZCMT6000, Long> {
    /**
     * 分页查找所有公司
     * */
    Page<ZCMT6000> findAll(Pageable pageable);
    /**
     * 分页查找所有子公司
     * */
    Page<ZCMT6000> findAllByBukrp(String bukrs, Pageable pageable);

    /**
     * 获取所有公司（不分页）
     * */
    List<ZCMT6000> findAll();
    /**
     * 根据公司代码返回公司
     * */
    ZCMT6000 findByBukrs(String bukrs);
    /**
     * 根据公司代码查找所有子公司
     * */
    List<ZCMT6000> findAllByBukrp(String bukrp);
    /**
     * 保存公司数据
     * */
    ZCMT6000 save(ZCMT6000 f);
    /**
     * 根据公司名称查询公司
     * */
    Page<ZCMT6000> findAllByBunamContainingIgnoringCase(String bunam, Pageable pageable);

}
