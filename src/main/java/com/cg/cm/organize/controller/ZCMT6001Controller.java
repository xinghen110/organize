package com.cg.cm.organize.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cg.cm.organize.entity.ZCMT6001;
import com.cg.cm.organize.entity.ZCMT6002;
import com.cg.cm.organize.service.ZCMT6001Service;
import com.cg.cm.organize.service.ZCMT6002Service;
import com.demo.cm.utils.BaseResponse;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hexin on 2018/2/6.
 *
 * 部门主数据维护Controller
 */

@RestController
public class ZCMT6001Controller {
    private ZCMT6001Service service;
    private ZCMT6002Service zcm6002service;
    private BaseResponse resp;

    public ZCMT6001Controller(ZCMT6001Service service, ZCMT6002Service zcm6002service) {
        this.service = service;
        this.zcm6002service = zcm6002service;
    }

    /**
     * 增加部门（不用）
     */
    @RequestMapping(value = "/department/adddepart" ,method = RequestMethod.POST)
    public String addDepart(@RequestBody ZCMT6001 zcmt6001){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6001.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6001.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            if(service.getDPNUM(zcmt6001.getDpnum()) != null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码已存在").toJSON();
            }
            zcmt6001.setCreateDate(Calendar.getInstance().getTime());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6001(zcmt6001)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 修改部门信息（不用）
     */
    @RequestMapping(value = "/department/updatedepartment" ,method = RequestMethod.POST)
    public String updateDepartment(@RequestBody ZCMT6001 zcmt6001){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6001.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6001.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            ZCMT6001 old = service.getDPNUM(zcmt6001.getDpnum());
            if( old == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不存在！").toJSON();
            }
            zcmt6001.setUpdateDate(Calendar.getInstance().getTime());
            zcmt6001.setCreateDate(old.getCreateDate());
            zcmt6001.setCreator(old.getCreator());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6001(zcmt6001)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 保存部门数据
     */
    @RequestMapping(value = "/department/savedepartment" ,method = RequestMethod.POST)
    public String saveDepartment(@RequestBody ZCMT6001 zcmt6001){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6001.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            if(Strings.isNullOrEmpty(zcmt6001.getDpnum())){
                zcmt6001.setCreateDate(Calendar.getInstance().getTime());
            }else{
                ZCMT6001 old = service.getDPNUM(zcmt6001.getDpnum());
                zcmt6001.setCreateDate(old.getCreateDate());
                zcmt6001.setUpdateDate(Calendar.getInstance().getTime());
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6001(zcmt6001)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 删除部门
     */
    @RequestMapping(value = "/department/deldepartment" ,method = RequestMethod.POST)
    public String deldePartment(@RequestBody ZCMT6001 dpnum){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(dpnum.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            ZCMT6001 zcmt6001 = service.getDPNUM(dpnum.getDpnum());
            if( zcmt6001== null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不存在！").toJSON();
            }
            // 挂有岗位的部门不可删除
            List<ZCMT6002> zcmt6002 = zcm6002service.getAll(dpnum.getDpnum());
            if(zcmt6002 != null && zcmt6002.size()>0){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("该部门下有"+zcmt6002.size()+"个岗位，不可删除！").toJSON();
            }
            // 删除部门
            service.deleteZCMT6001(zcmt6001);
            if(service.getDPNUM(dpnum.getDpnum()) == null){
                return resp.setStatecode(BaseResponse.SUCCESS).setMsg("删除成功！").toJSON();
            }else{
                return resp.setStatecode(BaseResponse.ERROR).setMsg("删除失败！").toJSON();
            }
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取部门列表
     */
    @RequestMapping(value = "/department/listdepartment" ,method = RequestMethod.POST)
    public String listDepartment(@RequestBody ZCMT6001 zcmt6001){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6001.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            List<ZCMT6001> list = service.getAll(zcmt6001.getBukrs());
            if( list== null){
                return resp.setStatecode(BaseResponse.EMPTY).setMsg("部门为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取子公司列表分页
     */
    @RequestMapping(value = "/department/pagedepartment" ,method = RequestMethod.POST)
    public String pageBusiness(@RequestBody String req){
        resp = new BaseResponse();
        JSONObject jsonObject = JSON.parseObject(req);
        if(Strings.isNullOrEmpty(jsonObject.getString("bukrs"))){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            Page<ZCMT6001> list = service.pageAll(jsonObject.getString("bukrs"),
                    new PageRequest(jsonObject.getIntValue("page"),jsonObject.getIntValue("size")));
            if( list== null){
                return resp.setStatecode(BaseResponse.EMPTY).setMsg("部门为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取单个部门
     */
    @RequestMapping(value = "/department/getdepartment" ,method = RequestMethod.POST)
    public String getBusiness(@RequestBody ZCMT6001 dpnum){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(dpnum.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            ZCMT6001 zcmt6001 = service.getDPNUM(dpnum.getDpnum());
            if(zcmt6001 == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不存在！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(zcmt6001).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
}
