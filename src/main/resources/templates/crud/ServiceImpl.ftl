package ${packageName}.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.utils.LayuiJson;
import ${packageName}.bean.${tableNameHump}Entity;
import ${packageName}.dao.${tableNameHump}Dao;
import ${packageName}.service.${tableNameHump}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ${tableRemark} - 服务实现
 *
 * @author ${author}
 * @version ${version}
 * @date ${date}
 */
@Service("${tableNameHump}Service")
public class ${tableNameHump}ServiceImpl extends ServiceImpl<${tableNameHump}Dao, ${tableNameHump}Entity> implements ${tableNameHump}Service {

	@Autowired
	private ${tableNameHump}Dao dao;

	/**
	 * 获取所有数据
	 *
	 * @param request 请求体
	 * @param param   请求参数字符串
	 * @param page    页码
	 * @param limit   条数
	 * @return
	 */
	@Override
	public LayuiJson getAllData(HttpServletRequest request, String param, int page, int limit) {
		//创建返回数据承载体
		LayuiJson json = new LayuiJson();

		//异常处理
		try {
			//获取当前用户
			WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
			//计算分页起始值
			int offset = (page - 1) * limit;

			//转换参数
			Map<String, Object> map = JSONUtil.toBean(param, Map.class);

			//设置分页数据
			map.put("offset", offset);
			map.put("limit", limit);

			//获取所有数据
			List<${tableNameHump}Entity> dataList = this.dao.getAllData(map);
			Long count = this.dao.getAllDataCount(map);

			//封装数据
			json.setMsg("获取数据成功！");
			json.setData(dataList);
			json.setCount(count + "");
			json.setSuccess(true);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			//封装数据
			json.setMsg("获取数据失败！");
			json.setCode(-1);
			return json;
		}
	}

}