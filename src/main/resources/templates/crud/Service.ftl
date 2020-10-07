package ${packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wkrj.core.utils.LayuiJson;
import ${packageName}.bean.${tableNameHump}Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ${tableRemark} - 服务接口
 *
 * @author ${author}
 * @version ${version}
 * @date ${date}
 */
public interface ${tableNameHump}Service extends IService<${tableNameHump}Entity> {

	/**
	 * 获取所有数据
	 *
	 * @param request 请求体
	 * @param param   请求参数字符串
	 * @param page    页码
	 * @param limit   条数
	 * @return
	 */
	LayuiJson getAllData(HttpServletRequest request, String param, int page, int limit);
}