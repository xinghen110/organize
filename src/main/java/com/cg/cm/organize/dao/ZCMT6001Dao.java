package com.cg.cm.organize.dao;

import com.cg.cm.organize.entity.ZCMT6001;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZCMT6001Dao extends CrudRepository<ZCMT6001, Long> {
    /**
     * 分页查找所有部门
     * */
    Page<ZCMT6001> findAllByBukrsOrderBySequeAsc(String bukrs , Pageable pageable);

    /**
     * 获取所有部门（不分页）
     * */
    List<ZCMT6001> findAllByBukrsOrderBySequeAsc(String bukrs);
    /**
     * 根据部门代码返回部门
     * */
    ZCMT6001 findByDpnum(String dpnum);
    /**
     * 根据部门名称查找部门
     * */
    List<ZCMT6001> findAllByDpnamContainingIgnoringCase(String dpnam);
    /**
     * 保存部门数据
     * */
    ZCMT6001 save(ZCMT6001 f);

}