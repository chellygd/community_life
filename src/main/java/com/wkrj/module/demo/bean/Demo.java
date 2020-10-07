package com.wkrj.module.demo.bean;


/**
 * 测试
 * @author ziro
 * @date 2019年5月28日
 */
public class Demo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2716726703496592321L;

    private String fileId;
    private String fileRealname;
    private String fileName;
    private String addtime;
    private String zbId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileRealname() {
        return fileRealname;
    }

    public void setFileRealname(String fileRealname) {
        this.fileRealname = fileRealname;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getZbId() {
        return zbId;
    }

    public void setZbId(String zbId) {
        this.zbId = zbId;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "fileId='" + fileId + '\'' +
                ", fileRealname='" + fileRealname + '\'' +
                ", fileName='" + fileName + '\'' +
                ", addtime='" + addtime + '\'' +
                ", zbId='" + zbId + '\'' +
                '}';
    }
}