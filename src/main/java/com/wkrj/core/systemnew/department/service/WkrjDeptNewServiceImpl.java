package com.wkrj.core.systemnew.department.service;

import com.wkrj.core.systemnew.department.bean.WkrjDeptNew;
import com.wkrj.core.systemnew.department.dao.WkrjDeptNewDao;
import com.wkrj.core.utils.LayuiJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("wkrjDeptNewService")
@Transactional
public class WkrjDeptNewServiceImpl implements WkrjDeptNewService {

    @Autowired
    private WkrjDeptNewDao wkrjDeptNewDao;

    @Override
    public List<Map<String, Object>> getRoleList(String parent_id, String dept_id) {

        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();//记录所有的module

        List<WkrjDeptNew> leftMenu = wkrjDeptNewDao.getDeptTree_new(parent_id, dept_id);

        for (WkrjDeptNew module : leftMenu) {

            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> attributesMap = new HashMap<String, Object>();

            map.put("id", module.getDeptId());
            map.put("text", module.getDeptName());
            map.put("value", module.getDeptId());
            map.put("name", module.getDeptName());
            attributesMap.put("dept_parent_id", module.getDeptParentId());
            attributesMap.put("dept_other", module.getDeptOther());
            attributesMap.put("dept_order", module.getDeptOrder());
            attributesMap.put("dept_id", module.getDeptId());
            attributesMap.put("dept_name", module.getDeptName());
            map.put("attributes", attributesMap);

            //不是叶子节点
            if (wkrjDeptNewDao.getDeptChildCount(module.getDeptId()) > 0) {
                //map.put("state", "closed");
                map.put("children", getRoleList(module.getDeptId(), dept_id));
            }

            all.add(map);
        }

        return all;

    }

    @Override
    public List<Map<String, Object>> getDeptAndSchoolGridList(String parent_id, boolean isGly, String user_dept) {
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        List<WkrjDeptNew> deptList = wkrjDeptNewDao.getDeptTree1(parent_id, isGly, user_dept);
		/*if(true==isGly){			
			if("-1".equals(parent_id)){
				deptList = wkrjDeptNewDao.getDeptTree2(parent_id, isGly, user_dept);
			}
		}	*/
        for (WkrjDeptNew module : deptList) {

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("id", module.getDeptId());
            //map.put("pid", "-1".equals(module.getDeptParentId())?null:module.getDeptParentId());
            map.put("pid", module.getDeptParentId());
            map.put("text", module.getDeptName());
            map.put("dept_other", module.getDeptOther());
            map.put("dept_order", module.getDeptOrder());
            map.put("pdept_name", module.getPdeptName());
            newlist.add(map);
            //不是叶子节点
            if (wkrjDeptNewDao.getDeptChildCount(module.getDeptId()) > 0) {
                newlist.addAll(getDeptAndSchoolGridList(module.getDeptId(), isGly, module.getDeptId()));
            }
            newlist = getnewlist(newlist);
        }
        return newlist;
    }

    public List<Map<String, Object>> getnewlist(List<Map<String, Object>> newlist) {
        ArrayList newList = new ArrayList<Object>();
        Iterator it = newlist.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (!newList.contains(obj)) {
                newList.add(obj);
            }
        }
        return newList;
    }

    @Override
    public boolean addDept(WkrjDeptNew dept) {
        String maxId = "001";
        if (null == dept.getDeptParentId() || "".equals(dept.getDeptParentId()) || "0".equals(dept.getDeptParentId())) {
            dept.setDeptParentId("-1");
            //通过父id找到下面自己节点中最大的然后加1
            maxId = wkrjDeptNewDao.getDeptChildMaxByPid("-1");
            dept.setDeptId(addOne(maxId));
        } else {
            maxId = wkrjDeptNewDao.getDeptChildMaxByPid(dept.getDeptParentId());
            if ("000".equals(maxId)) {
                maxId = dept.getDeptParentId() + maxId;
            }
            dept.setDeptId(addOne(maxId));
        }
        //加上这句判断是为了修改时而加的
		/*if(null==dept.getDeptId() || "".equals(dept.getDeptId())){
			dept.setDeptId(addOne(maxId));
		}*/
        return wkrjDeptNewDao.addDept(dept);
    }

    @Override
    public LayuiJson updateDept(WkrjDeptNew dept, String dept_parent_id_old) {
        LayuiJson json = new LayuiJson();
        json.setMsg("修改失败！");
        //查询
        if (dept_parent_id_old.equals(dept.getDeptParentId())) {
            //如果相同：证明上级部门未修改。。。修改基础信息
            boolean d = wkrjDeptNewDao.updateDept(dept);
            if (d) {
                json.setSuccess(true);
                json.setData("保存成功！");
                return json;
            }
        } else {
            //判断是否包含子元素
            int deptChildCount = wkrjDeptNewDao.getDeptChildCount(dept.getDeptId());
            if (deptChildCount > 0) {
                json.setSuccess(false);
                json.setData("包含子元素，无法修改上级部门！");
                return json;
            }
            //删除
            String dept_isdel = "1";
            boolean delDept_new = wkrjDeptNewDao.delDept_new(dept.getDeptId(), dept_isdel);
            if (delDept_new) {
                //新增
                boolean addDept = addDept(dept);
                if (addDept) {
                    json.setSuccess(true);
                    json.setData("保存成功！");
                    return json;
                }
            }
        }
        return json;
    }

    @Override
    public int getDeptChildCount(String parentId) {
        return wkrjDeptNewDao.getDeptChildCount(parentId);
    }

    @Override
    public boolean delDept(String dept_id) {
        String dept_isdel = "1";
        return wkrjDeptNewDao.delDept_new(dept_id, dept_isdel);
    }

    /**
     * 在原有数字字符串的基础上加1
     *
     * @param testStr
     * @return
     */
    public String addOne(String testStr) {
        String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
        String numStr = strs[strs.length - 1];//取出最后一组数字
        if (numStr != null && numStr.length() > 0) {//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
            int n = numStr.length();//取出字符串的长度
            long num = (Long.parseLong(numStr) + 1L);//将该数字加一
            String added = String.valueOf(num);
            n = Math.min(n, added.length());
            //拼接字符串
            return testStr.subSequence(0, testStr.length() - n) + added;
        } else {
            throw new NumberFormatException();
        }
    }

    @Override
    public List<Map<String, Object>> getDeptTree_lazy(String dept_id) {
        List<Map<String, Object>> deptTree_lazy = wkrjDeptNewDao.getDeptTree_lazy(dept_id);
        //判断是否包含子元素
        for (Map<String, Object> map : deptTree_lazy) {
            int deptChildCount = wkrjDeptNewDao.getDeptChildCount(map.get("dept_id") + "");
            if (deptChildCount == 0) {
                map.put("isLeaf", true);
            }
        }
        return deptTree_lazy;
    }
}
