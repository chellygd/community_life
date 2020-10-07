package ${packageName}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.utils.Guid;
import ${packageName}.service.${tableNameHump}Service;
import ${packageName}.bean.${tableNameHump}Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ${tableRemark} - 控制器
 *
 * @author ${author}
 * @version ${version}
 * @date ${date}
 */
@Controller
@RequestMapping("wkrj/${tableNameHump}")
public class ${tableNameHump}Controller {
	@Autowired
	private ${tableNameHump}Service service;

	/**
	 * 获取所有数据
	 *
	 * @param request 请求体
	 * @param param   请求参数字符串
	 * @param page    页码
	 * @param limit   条数
	 * @return
	 */
	@RequestMapping("getAllData")
	@ResponseBody
	public LayuiJson getAllData(HttpServletRequest request, String param, int page, int limit) {
		return service.getAllData(request, param, page, limit);
	}

	/**
	 * demo方法
	 * @return 结果json
	 */
	@RequestMapping("demo")
	@ResponseBody
	public LayuiJson demo() {
		LayuiJson json = new LayuiJson();
		json.setSuccess(true);
		json.setMsg("this is a demo!");
		json.setCount("0");
		json.setCode(0);
		json.setData("this is a demo!");
		return json;
	}

	/**
	* 新增
	*
	* @param entity
	* @return
	*/
	@RequestMapping("add")
	@ResponseBody
	public LayuiJson add(${tableNameHump}Entity entity) {
		LayuiJson j = new LayuiJson();
		try {
			entity.set${primaryKeyNameHump?cap_first}(Guid.getGuid());
			if (service.save(entity)) {
				j.setSuccess(true);
				j.setMsg("添加成功");
				return j;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	* 修改
	*
	* @param entity
	* @return
	*/
	@RequestMapping("update")
	@ResponseBody
	public LayuiJson update(${tableNameHump}Entity entity) {
		LayuiJson j = new LayuiJson();
		try {
			if (service.updateById(entity)) {
				j.setSuccess(true);
				j.setMsg("修改成功");
				return j;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	* 删除
	*
	* @param ids
	* @return
	*/
	@RequestMapping("delete")
	@ResponseBody
	public LayuiJson delete(String ids) {
		LayuiJson j = new LayuiJson();
		try {
			List<String> idlist = Arrays.asList(ids.split(","));
			if (service.removeByIds(idlist)) {
				j.setSuccess(true);
				j.setMsg("删除成功");
				return j;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
}
