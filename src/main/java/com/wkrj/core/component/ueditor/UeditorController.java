package com.wkrj.core.component.ueditor;

import com.wkrj.core.component.jwt.IgnoreToken;
import com.wkrj.core.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ueidtor编辑器配置
 *
 * @author ziro
 * @date 2020/9/17
 */
@Controller
@RequestMapping("ueditor")
public class UeditorController {


    @RequestMapping("/config")
    @ResponseBody
    public String ueditor(HttpServletRequest request) {
        return PublicMsg.UEDITOR_CONFIG;
    }

    /**
     * 富文本上传图片
     *
     * @param request
     * @return
     */
    @RequestMapping("uploadImage")
    @ResponseBody
    @IgnoreToken
    public Ueditor uploadImage(HttpServletRequest request) {
        Ueditor ueditor = new Ueditor();
        List<Map<String, String>> list = FileUtils.wkrj_uploadFile(request, "tempfolder");
        ueditor.setUrl(list.get(0).get("fileurl"));
        ueditor.setTitle(list.get(0).get("filename"));
        ueditor.setState("SUCCESS");
        ueditor.setOriginal(list.get(0).get("filename"));
        return ueditor;
    }


}