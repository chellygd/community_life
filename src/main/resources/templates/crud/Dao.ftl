package ${packageName}.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.bean.${tableNameHump}Entity;

import java.util.List;
import java.util.Map;

/**
 * ${tableRemark} - 数据接口
 *
 * @author ${author}
 * @version ${version}
 * @date ${date}
 */
public interface ${tableNameHump}Dao extends BaseMapper<${tableNameHump}Entity> {
    /**
     * 获取所有数据
     *
     * @param map 参数集合
     * @return 所有数据
     */
    List<${tableNameHump}Entity> getAllData(Map<String, Object> map);

    /**
     * 获取所有数据总数
     *
     * @param map 参数集合
     * @return 所有数据
     */
    Long getAllDataCount(Map<String, Object> map);
}