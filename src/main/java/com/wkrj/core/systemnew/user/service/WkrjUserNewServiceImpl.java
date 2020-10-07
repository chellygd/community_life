package com.wkrj.core.systemnew.user.service;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserRoleNew;
import com.wkrj.core.systemnew.user.dao.WkrjUserNewDao;
import com.wkrj.core.utils.Guid;
import com.wkrj.core.utils.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wkrjUserNewService")
@Transactional
public class WkrjUserNewServiceImpl implements WkrjUserNewService {

    @Autowired
    private WkrjUserNewDao wkrjUserNewDao;

    @Override
    public List<WkrjUserNew> getUserList(int offset, int limit, String deptId,
                                         String userName, String dept_id) {
        return wkrjUserNewDao.getUserList(offset, limit, deptId, userName, dept_id);
    }

    @Override
    public long getUserList(String deptId, String userName, String dept_id) {
        return wkrjUserNewDao.getUserListCount(deptId, userName, dept_id);
    }

    @Override
    public List<WkrjRoleNew> getRoleListByUserId(String user_id) {
        return wkrjUserNewDao.getRoleListByUserId(user_id);
    }

    @Override
    public List<Map<String, Object>> getRoleList() {
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjRoleNew> roles = wkrjUserNewDao.getRoleTree();

        for (WkrjRoleNew role : roles) {

            Map<String, Object> map = new HashMap<String, Object>();
            //Map<String,Object> attributesMap = new HashMap<String,Object>();

            map.put("value", role.getRoleId());
            map.put("name", role.getRoleName());

            all.add(map);
        }

        return all;
    }

    @Override
    public String addUser(WkrjUserNew module, String role) {
        //首先查询一下是否有重复的用户名、身份证号、工号，有的话用户名返回1，身份证号返回2，工号返回3正常返回0,其他返回4
        String str = "4";
        String userId = Guid.getGuid();

        if (0 < checkIsHaveNameOrGhOrSfz(module.getUserName(), userId)) {
            str = "1";
            return str;
        }
		/*if(0<checkIsHaveNameOrGhOrSfz(null,null,module.getUser_card(),userId)){
			str="2";
			return str;
		}*/
		/*if(0<checkIsHaveNameOrGhOrSfz(null,module.getUser_no(),null,userId)){
			str="3";
			return str;
		}*/

        //密码加密
        module.setUserPassword(MD5.MD5Encode(module.getUserPassword()));
        module.setUserAccounttype(0);

        boolean sus = true;

        if (StringUtils.isNotBlank(role)) {
            String[] split = role.split(",");
            for (String roleId : split) {
                WkrjUserRoleNew ur = new WkrjUserRoleNew();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                sus = wkrjUserNewDao.addUserRole(ur);
            }
        }

        if (sus) {

            module.setUserId(userId);
            module.setUserPasswordOld(module.getUserPassword());
            if (wkrjUserNewDao.addUser(module)) {
                str = "0";
            }
        }

        return str;
    }

    public long checkIsHaveNameOrGhOrSfz(String name, String userId) {
        return wkrjUserNewDao.checkIsHaveNameOrGhOrSfz(name, userId);
    }

    @Override
    public String updateUser(WkrjUserNew module, String role) {
        String userId = module.getUserId();
        String str = "4";

        if (0 < checkIsHaveNameOrGhOrSfz(module.getUserName(), userId)) {
            str = "1";
            return str;
        }

        boolean sus = true;

        boolean d = wkrjUserNewDao.delUerRoleById(userId);//删除角色

        if (StringUtils.isNotBlank(role)) {
            String[] split = role.split(",");
            for (String roleId : split) {
                WkrjUserRoleNew ur = new WkrjUserRoleNew();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                sus = wkrjUserNewDao.addUserRole(ur);
            }
        }

        if (sus) {
            if (wkrjUserNewDao.updateUser(module)) {
                str = "0";
            }
        }

        return str;
    }

    /**
     * 先删除用户角色再删除用户本身信息
     */
    @Override
    public boolean delUser(String id) {

        //boolean delUerRoleById = wkrjUserNewDao.delUerRoleById(id);

        return wkrjUserNewDao.delUser_new(id);
    }

    @Override
    public boolean forbiddenUser(String id, String flag) {
        return wkrjUserNewDao.forbiddenUser(id, flag);
    }

    @Override
    public boolean repeatPw(String id, String pw) {
        return wkrjUserNewDao.repeatPw(id, MD5.MD5Encode(pw));
    }

    @Override
    public boolean updatePassword(String userId, String md5Encode) {
        return wkrjUserNewDao.updatePassword(userId, md5Encode);
    }

    @Override
    public WkrjUserNew getUserInfoById(String user_id) {
        WkrjUserNew user = wkrjUserNewDao.getUserInfoById(user_id);
        return user;
    }

    @Override
    public boolean updateUserInfo(WkrjUserNew module) {
        return wkrjUserNewDao.updateUserInfo(module);
    }

    @Override
    public WkrjUserNew findUserInfoByName(String userName) {
        return wkrjUserNewDao.findUserInfoByName(userName);
    }

    @Override
    public WkrjUserNew findUserInfoByName(String userName, String password) {
        return wkrjUserNewDao.findUserInfoByNameandPw(userName, password);
    }

}
