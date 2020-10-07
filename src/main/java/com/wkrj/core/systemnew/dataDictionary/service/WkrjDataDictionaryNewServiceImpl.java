package com.wkrj.core.systemnew.dataDictionary.service;

import com.wkrj.core.systemnew.dataDictionary.bean.WkrjDataDictionaryNew;
import com.wkrj.core.systemnew.dataDictionary.dao.WkrjDataDictionaryNewDao;
import com.wkrj.core.systemnew.menu.dao.WkrjMenuNewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wkrjDataDictionaryNewService")
@Transactional
public class WkrjDataDictionaryNewServiceImpl implements WkrjDataDictionaryNewService {

	@Autowired
	private WkrjDataDictionaryNewDao wkrjDataDictionaryNewDao;

	@Override
	public List<Map<String, Object>> getDataDictionary(String node) {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listMaps = wkrjDataDictionaryNewDao.getDataDictionaryTree(node,"");
		for (int i=0;i<listMaps.size();i++) {
			Map<String,Object> mapfirst = new HashMap<String,Object>();
			mapfirst.put("id",  listMaps.get(i).get("id"));
			mapfirst.put("icon",  listMaps.get(i).get("icon"));
			mapfirst.put("sort",  listMaps.get(i).get("sort"));
			mapfirst.put("value",  listMaps.get(i).get("value"));
			mapfirst.put("typecode", listMaps.get(i).get("typecode"));
			mapfirst.put("typename", listMaps.get(i).get("typename"));
			mapfirst.put("typeparentid", listMaps.get(i).get("typeparentid"));
			mapfirst.put("ptypename", listMaps.get(i).get("ptypename"));
			
			//展示成树状结构
			mapfirst.put("id", listMaps.get(i).get("id"));
			mapfirst.put("text", listMaps.get(i).get("typename"));
			arrayList.add(mapfirst);
			
			List<Map<String, Object>> childrenlist = getDataDictionary(listMaps.get(i).get("id").toString());
			arrayList.addAll(childrenlist);
			/*if(childrenlist!=null && childrenlist.size()>0){
				mapfirst.put("children", childrenlist);
				mapfirst.put("state", "closed");				
			}*/
			
		}
		return arrayList;
	}

	@Override
	public List<Map<String, Object>> getDataDictionaryTree(String parentId,String id) {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listMaps = wkrjDataDictionaryNewDao.getDataDictionaryTree(parentId,id);
		for (int i=0;i<listMaps.size();i++) {
			Map<String,Object> mapfirst = new HashMap<String,Object>();
			mapfirst.put("id",  listMaps.get(i).get("id"));
			mapfirst.put("name",  listMaps.get(i).get("typename"));
			mapfirst.put("icon",  listMaps.get(i).get("icon"));
			mapfirst.put("typecode", listMaps.get(i).get("typecode"));
			mapfirst.put("typename", listMaps.get(i).get("typename"));
			mapfirst.put("typeparentid", listMaps.get(i).get("typeparentid"));
			//展示成树状结构
			mapfirst.put("value", listMaps.get(i).get("id"));
			mapfirst.put("name", listMaps.get(i).get("typename"));
			List<Map<String, Object>> childrenlist = getDataDictionaryTree(listMaps.get(i).get("id").toString(),id);
			if(childrenlist!=null && childrenlist.size()>0){
				mapfirst.put("children", childrenlist);
				mapfirst.put("state", "closed");				
			}
			arrayList.add(mapfirst);
		}
		return arrayList;
	}

	@Override
	public boolean addDataDictionary(WkrjDataDictionaryNew dd) {
		//dept.setDataDictionary_id(dept_id);
		String maxId="01";
		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
			//通过父id找到下面自己节点中最大的然后加1
			maxId = wkrjDataDictionaryNewDao.getDataDictionaryChildMaxByPid("-1");
			
		}else{
			maxId = wkrjDataDictionaryNewDao.getDataDictionaryChildMaxByPid(dd.getTypeparentid());
			if("00".equals(maxId)){
				maxId = dd.getTypeparentid()+maxId;
				dd.setId(addOne(maxId));
			}
		}
		//加上这句判断是为了修改时而加的
		if(null==dd.getId() || "".equals(dd.getId())){
			dd.setId(addOne(maxId));
		}

		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
		}
		if(dd.getSort()==null || "".equals(dd.getSort())){
			dd.setSort("0");
		}
		return wkrjDataDictionaryNewDao.addDataDictionary(dd);
	}
	/**
	 * 在原有数字字符串的基础上加1
	 * @param testStr
	 * @return
	 */
	public String addOne(String testStr){
	    String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	    String numStr = strs[strs.length-1];//取出最后一组数字
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	        int n = numStr.length();//取出字符串的长度
	        long num = (Long.parseLong(numStr)+1L);//将该数字加一
	        String added = String.valueOf(num);
	        n = Math.min(n, added.length());
	        //拼接字符串
	        return testStr.subSequence(0, testStr.length()-n)+added;
	    }else{
	        throw new NumberFormatException();
	    }
	}

	@Override
	public boolean updateDataDictionary(WkrjDataDictionaryNew dd) {
		//更新
		if(null==dd.getTypeparentid() || "".equals(dd.getTypeparentid())){
			dd.setTypeparentid("-1");
		}
		if(dd.getSort()==null || "".equals(dd.getSort())){
			dd.setSort("0");
		}
		
		if(wkrjDataDictionaryNewDao.updateDataDictionary(dd)){
			return true;
		}
		
		return false;
	}

	@Override
	public int getDataDictionaryChildCount(String parentId) {
		return wkrjDataDictionaryNewDao.getDataDictionaryChildCount(parentId);
	}

	@Override
	public boolean delDataDictionary(String id) {
		return wkrjDataDictionaryNewDao.delDataDictionary(id);
	}
}
