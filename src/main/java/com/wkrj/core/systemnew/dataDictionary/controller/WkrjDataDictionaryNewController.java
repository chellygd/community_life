package com.wkrj.core.systemnew.dataDictionary.controller;

import com.wkrj.core.systemnew.dataDictionary.bean.WkrjDataDictionaryNew;
import com.wkrj.core.systemnew.dataDictionary.service.WkrjDataDictionaryNewService;
import com.wkrj.core.utils.LayuiJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("wkrjsystemnew/wkrjDataDictionary")
public class WkrjDataDictionaryNewController {
    @Autowired
    private WkrjDataDictionaryNewService dataDictionaryService;

    /**
     * 数据字典 ==列表
     *
     * @param parentId
     * @return
     */
    @RequestMapping("getDataDictionary")
    @ResponseBody
    public LayuiJson getDataDictionary(String parentId) {
        LayuiJson json = new LayuiJson();
        if (null == parentId || "".equals(parentId)) {
            parentId = "-1";
        }
        List<Map<String, Object>> list = dataDictionaryService.getDataDictionary(parentId);
        json.setData(list);
        return json;
    }

    /**
     * 数据字典==树
     *
     * @param parentId
     * @return
     */
    @RequestMapping("getDataDictionaryTree")
    @ResponseBody
    public LayuiJson getDataDictionaryTree(String parentId, String id) {
        if (null == parentId || "".equals(parentId)) {
            parentId = "-1";
        }
        List<Map<String, Object>> list = dataDictionaryService.getDataDictionaryTree(parentId, id);
        LayuiJson json = new LayuiJson();
        json.setData(list);
        return json;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 数据字典==添加
     *
     * @param dd
     * @return
     */
    @RequestMapping("addDataDictionary")
    @ResponseBody
    public LayuiJson addDataDictionary(WkrjDataDictionaryNew dd) {

        LayuiJson json = new LayuiJson();

        //查询名称是否存在
        int count = jdbcTemplate.queryForObject("SELECT count(*) from wkrj_sys_data_dictionary where typename=?", new Object[]{dd.getTypename()}, Integer.class);
        if (count > 0) {
            json.setMsg("已存在！");
            json.setSuccess(false);
            return json;
        }

        if (dataDictionaryService.addDataDictionary(dd)) {
            json.setSuccess(true);
            json.setMsg("保存成功");
        }

        return json;
    }

    /**
     * 数据字典==修改
     *
     * @param dd
     * @return
     */
    @RequestMapping("updateDataDictionary")
    @ResponseBody
    public LayuiJson updateDataDictionary(WkrjDataDictionaryNew dd) {

        LayuiJson json = new LayuiJson();
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM `wkrj_sys_data_dictionary` where typeparentid=? and id=? ", new Object[]{dd.getTypeparentid(), dd.getId()}, Integer.class);
        //修改  如果修改父节点  不允许修改
        if (count == 0) {
            json.setSuccess(false);
            json.setMsg("不允许修改了上级字典，请先删除再重新添加！");
            return json;
        }
        if (dataDictionaryService.updateDataDictionary(dd)) {
            json.setSuccess(true);
            json.setMsg("保存成功");
        }
        return json;
    }

    /**
     * 数据字典==删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delDataDictionary")
    @ResponseBody
    public LayuiJson delDataDictionary(String id) {

        LayuiJson json = new LayuiJson();
        //首先判断是否还有孩子
        if (dataDictionaryService.getDataDictionaryChildCount(id) <= 0) {
            if (dataDictionaryService.delDataDictionary(id)) {
                json.setSuccess(true);
                json.setMsg("删除成功");
                return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("请先删除子节点");
            return json;
        }
        return json;
    }
}
