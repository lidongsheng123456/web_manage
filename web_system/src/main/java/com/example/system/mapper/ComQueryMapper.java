package com.example.system.mapper;

import com.example.system.domain.ComQuery;
import com.example.system.domain.Dict;
import com.example.system.domain.vo.DictVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ComQueryMapper {
    @Select("select dict_label,dict_value,tag_type from sys_dict_data where dict_type = #{dictType}")
    List<Dict> queryDictByType(String dictType);

    @Select("select custom_sql from sys_com_query where code = #{code}")
    ComQuery queryComQueryByCode(String code);

    List<DictVo> queryComQueryByCustomSql(String customSql);
}
