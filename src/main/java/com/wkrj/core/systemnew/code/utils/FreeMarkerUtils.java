package com.wkrj.core.systemnew.code.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 代码生成器 - 模版引擎工具类
 *
 * @author Harry
 * @version 1.0
 * @date 2020-08-06
 */
public class FreeMarkerUtils {

    private FreeMarkerUtils() {
    }

    private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

    static {
        //将crud下的文件放到resources目录下就可以了。需要修改目录自己配置即可
        configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtils.class, "/templates/crud"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    /**
     * 获取模版
     *
     * @param templateName 模版名称
     * @return
     * @throws Exception
     */
    public static Template getTemplate(String templateName) throws Exception {
        try {
            return configuration.getTemplate(templateName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        configuration.clearTemplateCache();
    }
}
