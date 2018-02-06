package com.cg.cm.organize.controller;

import com.alibaba.fastjson.JSONObject;
import com.cg.cm.organize.entity.ZCMT6000;
import com.cg.cm.organize.service.ZCMT6000Service;
import com.demo.cm.utils.BaseResponse;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hexin on 2018/1/24.
 *
 * 公司主数据维护Controller
 */

@RestController
public class ZCMT6000Controller {

    @Autowired
    private ZCMT6000Service service;

    private BaseResponse resp;

    /**
     * 增加公司
     */
    @RequestMapping(value = "/business/addbusiness" ,method = RequestMethod.POST)
    public String addBusiness(@RequestBody ZCMT6000 zcmt6000){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6000.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            if(service.getBUKRS(zcmt6000.getBukrs()) != null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码已存在").toJSON();
            }
            zcmt6000.setCreateDate(Calendar.getInstance().getTime());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6000(zcmt6000)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 修改公司
     */
    @RequestMapping(value = "/business/updatebusiness" ,method = RequestMethod.POST)
    public String updateBusiness(@RequestBody ZCMT6000 zcmt6000){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6000.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            ZCMT6000 old = service.getBUKRS(zcmt6000.getBukrs());
            if( old == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不存在！").toJSON();
            }
            zcmt6000.setUpdateDate(Calendar.getInstance().getTime());
            zcmt6000.setCreateDate(old.getCreateDate());
            zcmt6000.setCreator(old.getCreator());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6000(zcmt6000)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 保存公司
     */
    @RequestMapping(value = "/business/savebusiness" ,method = RequestMethod.POST)
    public String saveBusiness(@RequestBody ZCMT6000 zcmt6000){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6000.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            ZCMT6000 old = service.getBUKRS(zcmt6000.getBukrs());
            if( old == null){
                zcmt6000.setCreateDate(Calendar.getInstance().getTime());
            }else{
                zcmt6000.setCreateDate(old.getCreateDate());
                zcmt6000.setUpdateDate(Calendar.getInstance().getTime());
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6000(zcmt6000)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 删除公司
     */
    @RequestMapping(value = "/business/delbusiness" ,method = RequestMethod.POST)
    public String delBusiness(@RequestBody ZCMT6000 bukrs){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(bukrs.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            ZCMT6000 zcmt6000 = service.getBUKRS(bukrs.getBukrs());
            if( zcmt6000== null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不存在！").toJSON();
            }
            int size = service.getChild(bukrs.getBukrs()).size();
            if(size>0){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("该公司有个"+size+"子公司，不可删除！").toJSON();
            }
            service.deleteZCMT6000(zcmt6000);
            if(service.getBUKRS(bukrs.getBukrs()) == null){
                return resp.setStatecode(BaseResponse.SUCCESS).setMsg("删除成功！").toJSON();
            }else{
                return resp.setStatecode(BaseResponse.ERROR).setMsg("删除失败！").toJSON();
            }
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取公司列表
     */
    @RequestMapping(value = "/business/listbusiness" ,method = RequestMethod.POST)
    public String listBusiness(){
        resp = new BaseResponse();
        try{
            List<ZCMT6000> list = service.getAll();
            if( list== null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取子公司列表分页
     */
    @RequestMapping(value = "/business/page_sub_business" ,method = RequestMethod.POST)
    public String pageBusiness(@RequestParam("page") int page,
                               @RequestParam("size") int size,
                               @RequestParam("bukrs") String bukrs){
        resp = new BaseResponse();
        try{
            if(bukrs == null){
                bukrs = "";
            }
            Page<ZCMT6000> list = service.getAllChild(bukrs, new PageRequest(page,size));
            if( list== null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取公司
     */
    @RequestMapping(value = "/business/getbusiness" ,method = RequestMethod.POST)
    public String getBusiness(@RequestBody ZCMT6000 bukrs){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(bukrs.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        try{
            ZCMT6000 zcmt6000 = service.getBUKRS(bukrs.getBukrs());
            if(zcmt6000 == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不存在！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(zcmt6000).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
}
