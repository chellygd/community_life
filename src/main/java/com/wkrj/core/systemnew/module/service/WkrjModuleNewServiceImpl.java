package com.wkrj.core.systemnew.module.service;

import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;
import com.wkrj.core.systemnew.module.dao.WkrjModuleNewDao;
import com.wkrj.core.systemnew.permission.service.WkrjPermissionNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wkrjModuleNewService")
@Transactional
public class WkrjModuleNewServiceImpl implements WkrjModuleNewService {
    @Autowired
    private WkrjModuleNewDao wkrjModuleNewDao;
    @Autowired
    private WkrjPermissionNewService wkrjPermissionNewService;

    @Override
    public List<Map<String, Object>> getGridInfo(String parentId) {
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjModuleNew> leftMenu = wkrjModuleNewDao.getGridInfo(parentId, "");

        for (WkrjModuleNew module : leftMenu) {

            Map<String, Object> map = new HashMap<String, Object>();

            //map.put("id", module.getModuleId());
            map.put("module_id", module.getModuleId());
            map.put("module_url", module.getModuleUrl());
            //map.put("name", module.getModuleName());
            //map.put("text", module.getModuleName());
            map.put("module_name", module.getModuleName());
            map.put("module_order", module.getModuleOrder());
            map.put("module_is_display", (true == module.isModuleIsDisplay() ? "隐藏" : "显示"));
            map.put("iconCls", module.getModuleIcon());
            map.put("module_icon", module.getModuleIcon());
            map.put("module_other", module.getModuleOther());
            map.put("module_parent_id", module.getModuleParentId());
            map.put("module_level", module.isModuleLevel());
            map.put("module_icon_new", module.getModuleIconNew());
            all.add(map);
            //不是叶子节点
            if (wkrjModuleNewDao.getGridInfoChildCount(module.getModuleId()) > 0) {
                //map.put("state", "closed");
                all.addAll(getGridInfo(module.getModuleId()));
            } else {
//				return ;
            }


        }

        return all;
    }

    @Override
    public List<Map<String, Object>> getGridInfo_tree(String parentId, String module_id) {
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjModuleNew> leftMenu = wkrjModuleNewDao.getGridInfo(parentId, module_id);

        for (WkrjModuleNew module : leftMenu) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("name", module.getModuleName());
            map.put("value", module.getModuleId());
            //不是叶子节点
            if (wkrjModuleNewDao.getGridInfoChildCount(module.getModuleId()) > 0) {
                List<Map<String, Object>> gridInfo_tree = getGridInfo_tree(module.getModuleId(), module_id);
                map.put("children", gridInfo_tree);
            }
            all.add(map);

        }

        return all;
    }

    @Override
    public boolean addModule(WkrjModuleNew module) {
        return wkrjModuleNewDao.addModule(module);
    }

    @Override
    public boolean updateModule(WkrjModuleNew module) {
        return wkrjModuleNewDao.updateModule(module);
    }

    @Override
    public int checkIsHaveChildren(String id) {
        return wkrjModuleNewDao.getGridInfoChildCount(id);
    }

    @Override
    public boolean delModule(String id) {
        if (wkrjPermissionNewService.delPermissionByModuleId(id)) {
            if (wkrjModuleNewDao.delModule(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Map<String, Object>> getDisplayGridInfo(String parentId) {

        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjModuleNew> leftMenu = wkrjModuleNewDao.getDisplayGridInfo(parentId);

        for (WkrjModuleNew module : leftMenu) {

            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> attrMap = new HashMap<String, Object>();

            map.put("id", module.getModuleId());
            map.put("module_id", module.getModuleId());
            map.put("module_url", module.getModuleUrl());
            map.put("name", module.getModuleName());
            map.put("text", module.getModuleName());
            map.put("title", module.getModuleName());
            map.put("module_name", module.getModuleName());
            map.put("module_order", module.getModuleOrder());
            map.put("module_is_display", (true == module.isModuleIsDisplay() ? "隐藏" : "显示"));
            map.put("iconCls", module.getModuleIcon());
            map.put("module_icon", module.getModuleIcon());
            map.put("module_icon_new", module.getModuleIconNew());
            map.put("module_other", module.getModuleOther());
            map.put("module_parent_id", module.getModuleParentId());
            map.put("module_level", module.isModuleLevel());
            map.put("is", module.isModuleIsDisplay());
            map.put("spread", true);

            attrMap.put("module_url", module.getModuleUrl());
            attrMap.put("module_parent_id", module.getModuleParentId());
            attrMap.put("module_other", module.getModuleOther());
            attrMap.put("module_order", module.getModuleOrder());
            attrMap.put("module_is_display", module.isModuleIsDisplay());

            map.put("attributes", attrMap);

            //不是叶子节点
            if (wkrjModuleNewDao.getDisplayGridInfoChildCount(module.getModuleId()) > 0) {
                //map.put("state", "closed");
                map.put("children", getDisplayGridInfo(module.getModuleId()));
            } else {
//				return ;
            }

            all.add(map);
        }

        return all;
    }

    @Override
    public List<Map<String, Object>> getIcon() {
        List<Map<String, Object>> icon = wkrjModuleNewDao.getIcon();
        return icon;
    }

    @Override
    public List<Map<String, Object>> getGridInfo_gly(String parentId) {
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjModuleNew> leftMenu = wkrjModuleNewDao.getGridInfo(parentId, "");

        for (WkrjModuleNew module : leftMenu) {

            Map<String, Object> map = new HashMap<String, Object>();

            //map.put("id", module.getModuleId());
            map.put("name", module.getModuleId());
            //map.put("name", module.getModuleUrl().substring(module.getModuleUrl().lastIndexOf("/")+1));
            //map.put("module_url", module.getModuleUrl());
            map.put("icon", module.getModuleIconNew());
            //map.put("text", module.getModuleName());
            map.put("title", module.getModuleName());

            map.put("parentId", parentId);
            //不是叶子节点
            if (wkrjModuleNewDao.getGridInfoChildCount(module.getModuleId()) > 0) {
                map.put("list", getGridInfo_gly(module.getModuleId()));
            } else {
                map.put("jump", module.getModuleUrl());
            }
            all.add(map);

        }

        return all;
    }


}
