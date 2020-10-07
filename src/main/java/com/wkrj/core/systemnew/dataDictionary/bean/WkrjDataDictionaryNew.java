package com.wkrj.core.systemnew.dataDictionary.bean;

import lombok.Data;

/**
 * 数据字典表
 * 对应数据库表：wkrj_sys_data_dictionary
 *
 * @author ziro
 */
@Data
public class WkrjDataDictionaryNew {

    private String id;
    private String typecode;
    private String typename;
    private String typeid;
    private String typeparentid;
    private String value;
    private String icon;
    private String sort;
    private String ptypename;
}
