package com.cg.cm.organize.dao;

import com.cg.cm.organize.entity.ZCMT6002;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZCMT6002Dao extends CrudRepository<ZCMT6002, Long> {
    /**
     * 分页查找所有岗位
     * */
    Page<ZCMT6002> findAllByDpnum(String dpnum, Pageable pageable);

    /**
     * 获取所有岗位（不分页）
     * */
    List<ZCMT6002> findAllByDpnum(String dpnum);

    /**
     * 获取所有岗位（不分页）
     * */
    List<ZCMT6002> findAllByBukrs(String bukrs);
    /**
     * 根据岗位代码返回岗位
     * */
    ZCMT6002 findByGwnum(String gwnum);
    /**
     * 根据部门代码、岗位名称查找岗位
     * */
    List<ZCMT6002> findAllByDpnumAndGwnamContainingIgnoringCase(String dpnum, String gwnam);
    /**
     * 保存岗位数据
     * */
    ZCMT6002 save(ZCMT6002 f);
    /**
     * 获取部门编号最大值
     * */
    @Query("select max(a.gwnum) from ZCMT6002 a where a.gwnum like ?1%")
    String getMaxGwnum(String dpnum);
}
