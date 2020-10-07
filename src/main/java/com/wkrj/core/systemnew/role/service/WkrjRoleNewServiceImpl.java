package com.wkrj.core.systemnew.role.service;

import com.wkrj.core.configuration.security.MyInvocationSecurityMetadataSourceService;
import com.wkrj.core.systemnew.menu.bean.WkrjMenuNew;
import com.wkrj.core.systemnew.menu.dao.WkrjMenuNewDao;
import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;
import com.wkrj.core.systemnew.permission.dao.WkrjPermissionNewDao;
import com.wkrj.core.systemnew.role.bean.WkrjRoleMenuNew;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.role.bean.WkrjRolePermissionNew;
import com.wkrj.core.systemnew.role.dao.WkrjRoleNewDao;
import com.wkrj.core.utils.Guid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wkrjRoleNewService")
@Transactional
public class WkrjRoleNewServiceImpl implements WkrjRoleNewService {

    @Autowired
    private WkrjRoleNewDao wkrjRoleNewDao;

    @Autowired
    private WkrjMenuNewDao wkrjMenuNewDao;

    @Autowired
    private WkrjPermissionNewDao wkrjPermissionNewDao;

    @Autowired
    private MyInvocationSecurityMetadataSourceService invocationSecurityMetadataSourceService;

    @Override
    public List<WkrjRoleNew> getRoleList(int offset, int page, String dept_id) {
        return wkrjRoleNewDao.getRoleList(offset, page, dept_id);
    }

    @Override
    public long getRoleList(String dept_id) {
        return wkrjRoleNewDao.getRoleListCount(dept_id);
    }

    @Override
    public boolean addRole(WkrjRoleNew module) {
        return wkrjRoleNewDao.addRole(module);
    }

    @Override
    public boolean updateRole(WkrjRoleNew module) {
        return wkrjRoleNewDao.updateRole(module);
    }

    @Override
    public boolean delRole(String id) {

        boolean sus = true;
		
		/*//删除菜单
		if(wkrjRoleNewDao.getRoleMenuByRoleId(id).size()>0){
			sus=wkrjRoleNewDao.delRoleMenu(id);
			if(false==sus)return false;
		}
		//删除权限
		if(wkrjRoleNewDao.getRolePermissionByRoleId(id).size()>0){
			sus=wkrjRoleNewDao.delRolePermission(id);
			if(false==sus)return false;
		}*/

        //if(sus){
        //return wkrjRoleNewDao.delRole(id);
        //}
        String role_isdel = "1";
        return wkrjRoleNewDao.delRoleNew(id, role_isdel);
        //return false;


    }

    @Override
    public boolean copyRole(String role_id) {
        //首先复制角色的列表然后再复制角色的权限
        WkrjRoleNew role = wkrjRoleNewDao.getRoleById(role_id);
        String id = Guid.getGuid();
        role.setRoleId(id);

        boolean sus = true;

        if (wkrjRoleNewDao.addRole(role)) {

            //添加菜单
            List<WkrjRoleMenuNew> roleMenu = wkrjRoleNewDao.getRoleMenuByRoleId(role_id);
            if (roleMenu.size() > 0) {
                for (WkrjRoleMenuNew rm : roleMenu) {
                    rm.setRoleId(id);
                    if (!wkrjRoleNewDao.saveRoleMenu(rm)) {
                        sus = false;
                        return sus;
                    }
                }
            }

            //添加权限
            List<WkrjRolePermissionNew> rolePerm = wkrjRoleNewDao.getRolePermissionByRoleId(role_id);
            if (rolePerm.size() > 0) {
                for (WkrjRolePermissionNew rp : rolePerm) {
                    rp.setRoleId(id);
                    if (!wkrjRoleNewDao.saveRolePermission(rp)) {
                        sus = false;
                        return sus;
                    }
                }
            }


        } else {
            sus = false;
        }

        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return sus;
    }

    @Override
    public Object getMenuPermission(String role_id, String parentId) {
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的menu
        List<WkrjMenuNew> allMenu = wkrjMenuNewDao.getAllMenu(parentId);//获取所有的菜单通过parentId
        List<WkrjRoleMenuNew> allRoleMenu = wkrjRoleNewDao.getRoleMenuByRoleId(role_id);//获取角色菜单

        for (WkrjMenuNew menu : allMenu) {

            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> attrMap = new HashMap<String, Object>();

            map.put("value", menu.getMenuId());
            //map.put("menu_url", menu.getMenuUrl());
            //map.put("name", menu.getMenuName());
            map.put("title", menu.getMenuName());
            //map.put("iconCls", menu.getMenuIcon());
            map.put("menu_id", menu.getMenuId());
            map.put("data", "");
            map.put("menu_order", menu.getMenuOrder());

            //获取所有的权限通过模块Id
            List<WkrjPermissionNew> allWkrjPermission = wkrjPermissionNewDao.getPermissionByModuleId(menu.getModuleId());
            List<Map<String, Object>> listPermissionMap = new ArrayList<Map<String, Object>>();

            if (allWkrjPermission.size() > 0) {

                for (WkrjPermissionNew perm : allWkrjPermission) {

                    Map<String, Object> permissionMap = new HashMap<String, Object>();
                    Map<String, Object> permAttrMap = new HashMap<String, Object>();

                    permissionMap.put("value", perm.getPermId());
                    permissionMap.put("title", perm.getPermName());
                    permissionMap.put("data", "");
                    permissionMap.put("permid", perm.getPermId());
                    permissionMap.put("menu_order", menu.getMenuOrder());
                    //permAttrMap.put("permid", perm.getPermId());
                    //permissionMap.put("data",permAttrMap);

                    //通过角色id获取对应的权限
                    List<WkrjRolePermissionNew> listRolePermission = wkrjRoleNewDao.getRolePermissionByRoleId(role_id);
                    if (listRolePermission.size() > 0 && (null != role_id && !"".equals(role_id))) {

                        for (WkrjRolePermissionNew rolePerm : listRolePermission) {

                            if (perm.getPermId().equals(rolePerm.getPermId())) {
                                permissionMap.put("checked", true);
                            }
                        }

                    }

                    listPermissionMap.add(permissionMap);

                }

                map.put("data", listPermissionMap);


            }


            //不是叶子节点
            if (wkrjMenuNewDao.getAllMenuChildCount(menu.getMenuId()) > 0) {
                //map.put("state", "closed");

                if (allRoleMenu.size() > 0 && (null != role_id && !"".equals(role_id))) {

                    for (WkrjRoleMenuNew roleMenu : allRoleMenu) {
                        //角色中有的模块则被选中
                        if (roleMenu.getMenuId().equals(menu.getMenuId())) {
                            map.put("checked", true);
                        }
                    }

                }

                map.put("data", this.getMenuPermission(role_id, menu.getMenuId()));
            } else {

                if (allRoleMenu.size() > 0 && (null != role_id && !"".equals(role_id))) {

                    for (WkrjRoleMenuNew roleMenu : allRoleMenu) {
                        //角色中有的模块则被选中
                        if (roleMenu.getMenuId().equals(menu.getMenuId())) {
                            map.put("checked", true);
                        }
                    }

                }

            }

            all.add(map);
        }

        return all;
    }

    @Override
    public boolean setMenuPermission(String menulist, String permissionlist,
                                     String roler_id, String menu_order) {
        boolean sus = false;

        //首先删除原先的菜单和权限，以免冲突
        wkrjRoleNewDao.delRoleMenu(roler_id);
        wkrjRoleNewDao.delRolePermission(roler_id);

        //保存菜单
        if (!"".equals(menulist)) {

            for (int i = 0; i < menulist.split(",").length; i++) {

                WkrjRoleMenuNew roleMenu = new WkrjRoleMenuNew();
                List<WkrjMenuNew> list = wkrjMenuNewDao.getMenuById(menulist.split(",")[i]);
                roleMenu.setMenuId(menulist.split(",")[i]);
                roleMenu.setRoleId(roler_id);

                //roleMenu.setMenuId(menulist.split(",")[i]);
                roleMenu.setRoleId(roler_id);
                roleMenu.setMenuOrder(menu_order.split(",")[i]);
                sus = wkrjRoleNewDao.saveRoleMenu(roleMenu);
                if (sus == false) {
                    return false;
                }
            }

        } else {
            sus = true;
        }

        //保存权限
        if (!"".equals(permissionlist)) {

            for (int i = 0; i < permissionlist.split(",").length; i++) {

                WkrjRolePermissionNew rolePerm = new WkrjRolePermissionNew();
                rolePerm.setPermId(permissionlist.split(",")[i]);
                rolePerm.setRoleId(roler_id);

                sus = wkrjRoleNewDao.saveRolePermission(rolePerm);
                if (sus == false) {
                    return false;
                }
            }

        } else {
            sus = true;
        }

        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return sus;
    }

    @Override
    public String getAllPermission() {

        String permStr = "";


        //通过角色id获取对应的权限
        List<WkrjPermissionNew> listRolePermission = wkrjRoleNewDao.getAllPermission();
        if (listRolePermission.size() > 0) {

            for (WkrjPermissionNew perm : listRolePermission) {

                permStr += perm.getPermFlag();
                permStr += ",";

            }
        }

        if (!"".equals(permStr)) {
            permStr = permStr.substring(0, permStr.length() - 1);
        }

        return permStr;
    }

    @Override
    public String getRolePermissionByRoleId(String role_id) {

        String permStr = "";

        if (null != role_id && !"".equals(role_id)) {

            for (int i = 0; i < role_id.split(",").length; i++) {
                //通过角色id获取对应的权限
                List<WkrjRolePermissionNew> listRolePermission = wkrjRoleNewDao.getRolePermissionByRoleId(role_id.split(",")[i]);
                if (listRolePermission.size() > 0) {

                    for (WkrjRolePermissionNew rolePerm : listRolePermission) {

                        List<WkrjPermissionNew> perms = wkrjPermissionNewDao.getPermissionByPermId(rolePerm.getPermId());
                        if (perms.size() > 0) {

                            for (WkrjPermissionNew perm : perms) {
                                permStr += perm.getPermFlag();
                                permStr += ",";
                            }

                        }
                    }
                }
            }
        }

        if (!"".equals(permStr)) {
            permStr = permStr.substring(0, permStr.length() - 1);
        }

        return permStr;
    }

}
