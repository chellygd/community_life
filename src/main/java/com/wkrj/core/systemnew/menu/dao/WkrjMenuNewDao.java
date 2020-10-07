package com.wkrj.core.systemnew.menu.dao;

import com.wkrj.core.systemnew.menu.bean.WkrjMenuNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WkrjMenuNewDao {

    List<WkrjMenuNew> getAllMenu(String parentId);

    int getAllMenuChildCount(String menu_id);

    boolean addMenu(WkrjMenuNew m);

    boolean updateMenu(WkrjMenuNew m);

    boolean delMenu(String id);

    int getGridInfoChildCount(String parentId);

    List<WkrjMenuNew> getMenuById(String menu_id);


    List<WkrjMenuNew> getMenuById1(@Param("menu_id") String id, @Param("parentId") String parentId);

    List<WkrjMenuNew> checkNodeisLeaf(@Param("parentId") String parentId);


}
