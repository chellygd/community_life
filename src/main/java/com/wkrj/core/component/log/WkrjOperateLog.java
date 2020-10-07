package com.wkrj.core.component.log;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体类
 * @author ziro
 * @date 2019/8/7 11:24
 */
public class WkrjOperateLog implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2814502715652393352L;

    //操作日志ID
    private Integer id;
    //操作人ID
    private String userId;
    //操作人
    private String userName;
    //操作模块
    private String logmethod;
    //执行方法
    private String logmsg;
    //操作内容
    private String content;
    //请求路径
    private String actionurl;
    //IP
    private String ip;
    //操作时间
    private Date addtime;
    //执行描述（1:执行成功、2:执行失败）
    private Byte commite;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogmethod() {
        return logmethod;
    }

    public void setLogmethod(String logmethod) {
        this.logmethod = logmethod;
    }

    public String getLogmsg() {
        return logmsg;
    }

    public void setLogmsg(String logmsg) {
        this.logmsg = logmsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActionurl() {
        return actionurl;
    }

    public void setActionurl(String actionurl) {
        this.actionurl = actionurl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Byte getCommite() {
        return commite;
    }

    public void setCommite(Byte commite) {
        this.commite = commite;
    }
}
