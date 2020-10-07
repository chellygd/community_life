package com.wkrj.core.systemnew.menu.service;

import com.wkrj.core.systemnew.menu.bean.WkrjMenuNew;
import com.wkrj.core.systemnew.menu.dao.WkrjMenuNewDao;
import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;
import com.wkrj.core.systemnew.module.dao.WkrjModuleNewDao;
import com.wkrj.core.systemnew.role.bean.WkrjRoleMenuNew;
import com.wkrj.core.systemnew.role.dao.WkrjRoleNewDao;
import com.wkrj.core.utils.Guid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wkrjMenuNewService")
@Transactional
public class WkrjMenuNewServiceImpl implements WkrjMenuNewService {
    @Autowired
    private WkrjMenuNewDao wkrjMenuNewDao;
    @Autowired
    private WkrjModuleNewDao wkrjModuleNewDao;
    @Autowired
    private WkrjRoleNewDao wkrjRoleNewDao;

    @Override
    public List<Map<String, Object>> getGridInfo(String parentId) {

        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的menu
        List<WkrjMenuNew> leftMenu = wkrjMenuNewDao.getAllMenu(parentId);
        for (WkrjMenuNew menu : leftMenu) {

            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> attrMap = new HashMap<String, Object>();

            map.put("id", menu.getMenuId());
            map.put("value", menu.getMenuId());
            map.put("menu_id", menu.getMenuId());
            map.put("menu_url", menu.getMenuUrl());
            map.put("name", menu.getMenuName());
            map.put("text", menu.getMenuName());
            map.put("title", menu.getMenuName());
            map.put("menu_name", menu.getMenuName());
            map.put("menu_order", menu.getMenuOrder());
            map.put("menu_is_display", (true == menu.isMenuIsDisplay() ? "隐藏" : "显示"));
            map.put("iconCls", menu.getMenuIcon());
            map.put("menu_icon", menu.getMenuIcon());

            map.put("menu_icon_new", menu.getMenuIconNew());
            map.put("menu_other", menu.getMenuOther());
            map.put("menu_parent_id", menu.getMenuParentId());
            map.put("menu_level", menu.isMenuLevel());
            map.put("pmenu_name", menu.getPmenuName());
            map.put("is", menu.isMenuIsDisplay());
            map.put("spread", true);

            attrMap.put("menu_url", menu.getMenuUrl());
            attrMap.put("iconCls", menu.getMenuIcon());
            attrMap.put("menu_parent_id", menu.getMenuParentId());
            attrMap.put("menu_other", menu.getMenuOther());
            attrMap.put("menu_order", menu.getMenuOrder());
            attrMap.put("menu_is_display", menu.isMenuIsDisplay());
            attrMap.put("wkrjModule", wkrjModuleNewDao.getWkrjModuleById(menu.getModuleId()));
            map.put("attributes", attrMap);

            //不是叶子节点
            if (wkrjMenuNewDao.getAllMenuChildCount(menu.getMenuId()) > 0) {
                //map.put("state", "closed");
                map.put("children", getGridInfo(menu.getMenuId()));
            }

            all.add(map);
        }

        return all;
    }

    @Override
    public boolean addMenu(WkrjModuleNew module) {
        WkrjMenuNew m = module.getMenu().get(0);
        m.setMenuId(Guid.getGuid());
        m.setModuleId(module.getModuleId());
        m.setMenuUrl(module.getModuleUrl());
//		m.setMenu_icon(copyFile(module.getModuleIcon()));

        return wkrjMenuNewDao.addMenu(m);
    }

    @Override
    public boolean updateMenu(WkrjModuleNew module, String yFileName) {

        WkrjMenuNew m = module.getMenu().get(0);
        m.setModuleId((StringUtils.isEmpty(module.getModuleId())) ? "-1" : module.getModuleId());
        m.setMenuUrl(module.getModuleUrl());
        m.setMenuParentId((StringUtils.isEmpty(m.getMenuParentId())) ? "-1" : m.getMenuParentId());
        return wkrjMenuNewDao.updateMenu(m);
    }

    @Override
    public boolean delMenu(String id, String icon) {
        try {

            if (wkrjMenuNewDao.delMenu(id)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int checkIsHaveChildren(String id) {
        return wkrjMenuNewDao.getGridInfoChildCount(id);
    }

    @Override
    public List<Map<String, Object>> getLeftMenuByRole(String parentId,
                                                       String roleIds) {


        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的menu

        if (null != roleIds && !"".equals(roleIds)) {

            String roleChildMenuId = "";//记录菜单拥有的子节点的id
            String roleMenuId = "";//记录角色所拥有的菜单的id

            List<WkrjRoleMenuNew> roleMenus = wkrjRoleNewDao.getMenuByRoleIds(roleIds.split(","));

            if (roleMenus.size() > 0) {

                for (WkrjRoleMenuNew roleMenu : roleMenus) {
                    roleMenuId += roleMenu.getMenuId();
                    roleMenuId += ",";
                }

                for (WkrjRoleMenuNew roleMenu : roleMenus) {

                    //List<WkrjMenuNew> menus = wkrjMenuNewDao.getMenuById1(roleMenu.getMenuId(),parentId);
                    List<WkrjMenuNew> menus = wkrjMenuNewDao.getMenuById1(roleMenu.getMenuId(), parentId);

                    if (menus.size() > 0) {

                        for (WkrjMenuNew menu : menus) {

                            Map<String, Object> map = new HashMap<String, Object>();
                            Map<String, Object> attributesMap = new HashMap<String, Object>();

                            map.put("name", menu.getMenuId());
                            //map.put("name", menu.getMenuUrl().substring(menu.getMenuUrl().lastIndexOf("/")+1));
                            map.put("title", menu.getMenuName());
                            map.put("icon", menu.getMenuIconNew());
                            //attributesMap.put("menu_url", menu.getMenuUrl());
                            //attributesMap.put("iconCls", menu.getMenuIcon());
                            map.put("attributes", attributesMap);

                            map.put("parentId", parentId);
                            //判断是不是叶子节点
                            List<WkrjMenuNew> gridInfo = wkrjMenuNewDao.checkNodeisLeaf(menu.getMenuId());
                            if (gridInfo.size() > 0) {

                                //进入后需要判断包括的子节点是不是被赋予了权限
                                for (WkrjMenuNew grid : gridInfo) {

                                    roleChildMenuId = grid.getMenuId();
                                    roleChildMenuId += ",";

                                    if (roleMenuId.indexOf(roleChildMenuId) >= 0) {
                                        //map.put("state", "closed");
                                        map.put("list", getLeftMenuByRole(menu.getMenuId(), roleIds));
                                    }

                                }

                            } else {
                                map.put("jump", menu.getMenuUrl());
                            }
                            all.add(map);
                        }

                    }

                }
            }

        }

        return all;
    }


}
