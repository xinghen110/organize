package com.cg.cm.organize.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cg.cm.organize.entity.ZCMT6002;
import com.cg.cm.organize.service.ZCMT6002Service;
import com.demo.cm.utils.BaseResponse;
import com.demo.cm.utils.CMException;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hexin on 2018/2/8.
 *
 * 岗位主数据维护Controller
 */

@RestController
public class ZCMT6002Controller {
    private ZCMT6002Service service;
    private BaseResponse resp;

    public ZCMT6002Controller(ZCMT6002Service service) {
        this.service = service;
    }

    /**
     * 增加岗位（不用）
     */
    @RequestMapping(value = "/gangwei/addgangwei" ,method = RequestMethod.POST)
    public String addGangwei(@RequestBody ZCMT6002 zcmt6002){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6002.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6002.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6002.getGwnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不能为空！").toJSON();
        }
        try{
            if(service.getGWNUM(zcmt6002.getGwnum()) != null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码已存在").toJSON();
            }
            zcmt6002.setCreateDate(Calendar.getInstance().getTime());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6002(zcmt6002)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 修改岗位信息（不用）
     */
    @RequestMapping(value = "/gangwei/updategangwei" ,method = RequestMethod.POST)
    public String updateGangwei(@RequestBody ZCMT6002 zcmt6002){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6002.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6002.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6002.getGwnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不能为空！").toJSON();
        }
        try{
            ZCMT6002 old = service.getGWNUM(zcmt6002.getGwnum());
            if( old == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不存在！").toJSON();
            }
            zcmt6002.setUpdateDate(Calendar.getInstance().getTime());
            zcmt6002.setCreateDate(old.getCreateDate());
            zcmt6002.setCreator(old.getCreator());
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6002(zcmt6002)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 保存岗位数据
     */
    @RequestMapping(value = "/gangwei/savegangwei" ,method = RequestMethod.POST)
    public String saveGangwei(@RequestBody ZCMT6002 zcmt6002){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6002.getBukrs())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("公司代码不能为空！").toJSON();
        }
        if(Strings.isNullOrEmpty(zcmt6002.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            if(Strings.isNullOrEmpty(zcmt6002.getGwnum())){
                zcmt6002.setCreateDate(Calendar.getInstance().getTime());
            }else{
                ZCMT6002 old = service.getGWNUM(zcmt6002.getDpnum());
                zcmt6002.setCreateDate(old.getCreateDate());
                zcmt6002.setUpdateDate(Calendar.getInstance().getTime());
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(service.saveZCMT6002(zcmt6002)).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
    /**
     * 删除岗位
     */
    @RequestMapping(value = "/gangwei/delgangwei" ,method = RequestMethod.POST)
    public String deldeGangwei(@RequestBody ZCMT6002 dpnum){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(dpnum.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不能为空！").toJSON();
        }
        try{
            ZCMT6002 zcmt6002 = service.getGWNUM(dpnum.getGwnum());
            if( zcmt6002== null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不存在！").toJSON();
            }
            // TODO 挂有用户的岗位不可删除
//            int size = service.getChild(bukrs.getBukrs()).size();
//            if(size>0){
//                return resp.setStatecode(BaseResponse.ERROR).setMsg("该公司有个"+size+"子公司，不可删除！").toJSON();
//            }
            service.deleteZCMT6002(zcmt6002);
            if(service.getGWNUM(dpnum.getGwnum()) == null){
                return resp.setStatecode(BaseResponse.SUCCESS).setMsg("删除成功！").toJSON();
            }else{
                return resp.setStatecode(BaseResponse.ERROR).setMsg("删除失败！").toJSON();
            }
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取岗位列表
     */
    @RequestMapping(value = "/gangwei/listgangwei" ,method = RequestMethod.POST)
    public String listGangwei(@RequestBody ZCMT6002 zcmt6002){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(zcmt6002.getDpnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            List<ZCMT6002> list = service.getAll(zcmt6002.getDpnum());
            if( list== null){
                return resp.setStatecode(BaseResponse.EMPTY).setMsg("岗位为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取岗位列表分页
     */
    @RequestMapping(value = "/gangwei/pagegangwei" ,method = RequestMethod.POST)
    public String pageGangwei(@RequestBody String req){
        resp = new BaseResponse();
        JSONObject jsonObject = JSON.parseObject(req);
        if(Strings.isNullOrEmpty(jsonObject.getString("dpnum"))){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("部门代码不能为空！").toJSON();
        }
        try{
            Page<ZCMT6002> list = service.pageAll(jsonObject.getString("dpnum"),
                    new PageRequest(jsonObject.getIntValue("page"),jsonObject.getIntValue("size")));
            if( list== null){
                return resp.setStatecode(BaseResponse.EMPTY).setMsg("岗位为空！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(list).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }

    /**
     * 获取单个岗位
     */
    @RequestMapping(value = "/gangwei/getgangwei" ,method = RequestMethod.POST)
    public String getGangwei(@RequestBody ZCMT6002 gwnum){
        resp = new BaseResponse();
        if(Strings.isNullOrEmpty(gwnum.getGwnum())){
            return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不能为空！").toJSON();
        }
        try{
            ZCMT6002 zcmt6002 = service.getGWNUM(gwnum.getGwnum());
            if(zcmt6002 == null){
                return resp.setStatecode(BaseResponse.ERROR).setMsg("岗位代码不存在！").toJSON();
            }
            return resp.setStatecode(BaseResponse.SUCCESS).setData(zcmt6002).toJSON();
        }catch (CMException e){
            return resp.setStatecode(BaseResponse.ERROR).setMsg(e.getMsg()).toJSON();
        }
    }
}
