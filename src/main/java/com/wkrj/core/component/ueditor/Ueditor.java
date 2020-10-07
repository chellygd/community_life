package com.wkrj.core.component.ueditor;
/**
 * @Auther: Sunshuwei
 * @Date: 2020/6/18 15:10
 */
public class Ueditor {
    private  String state;private  String url;private  String title;private  String original;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
    /**
     * Ueditor的返回状态类型
     */
    public enum UeditorMsg{
        SUCCESS("SUCCESS"),ERROR("上传失败");
        private String v;
        UeditorMsg(String v){
            this.v =v;
        }
        public String get(){
            return this.v;
        }
    }
}