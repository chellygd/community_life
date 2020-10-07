package com.wkrj.core.systemnew.permission.service;

import com.wkrj.core.configuration.security.MyInvocationSecurityMetadataSourceService;
import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;
import com.wkrj.core.systemnew.permission.dao.WkrjPermissionNewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("wkrjPermissionNewService")
@Transactional
public class WkrjPermissionNewServiceImpl implements WkrjPermissionNewService {
    @Autowired
    private WkrjPermissionNewDao wkrjPermissionNewDao;
    @Autowired
    private MyInvocationSecurityMetadataSourceService invocationSecurityMetadataSourceService;

    /**
     * 删除权限通过模块id
     * 首先删除记录然后再删除图标
     *
     * @param moduleId
     * @return
     */
    @Override
    public boolean delPermissionByModuleId(String moduleId) {
        //通过moduleId获取所有的权限
        List<WkrjPermissionNew> list = wkrjPermissionNewDao.getPermissionByModuleId(moduleId);
        boolean sus = true;
        if (list.size() > 0) {
            for (WkrjPermissionNew p : list) {
                sus = delPermission(p.getPermId(), p.getPermIcon());
                if (!sus) {
                    return false;
                }
            }
        } else {
            return sus;
        }
        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return sus;
    }

    @Override
    public boolean delPermission(String id, String icon) {
		
		/*if(wkrjPermissionNewDao.delPermission(id)){
			//return delIcon(icon);
		}*/
        boolean del = wkrjPermissionNewDao.delPermission(id);
        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return del;
    }

    @Override
    public List<WkrjPermissionNew> getPermissionList(int offset, int limit, String module_id) {
        return wkrjPermissionNewDao.getPermissionList(offset, limit, module_id);
    }

    @Override
    public long getPermissionList(String module_id) {
        return wkrjPermissionNewDao.getPermissionListCount(module_id);
    }

    @Override
    public boolean checkIsHavePermission(String perm_flag, String id) {
        return wkrjPermissionNewDao.checkIsHavePermission(perm_flag, id);
    }

    @Override
    public boolean addPermission(WkrjPermissionNew perm) {
        boolean add = wkrjPermissionNewDao.addPermission(perm);
        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return add;
    }

    @Override
    public boolean updatePermission(WkrjPermissionNew perm) {
        boolean update = wkrjPermissionNewDao.updatePermission(perm);
        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return update;
    }

    @Override
    public boolean delPermission(String id) {
        boolean del = wkrjPermissionNewDao.delPermission(id);
        //更新security权限的授权
        invocationSecurityMetadataSourceService.loadResourceDefine();
        return del;
    }

}