package com.example.system.service.impl;

import com.example.system.domain.ComQuery;
import com.example.system.domain.Dict;
import com.example.system.domain.vo.DictVo;
import com.example.system.mapper.ComQueryMapper;
import com.example.system.service.ComQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComQueryServiceImpl implements ComQueryService {
    private final ComQueryMapper ComQueryMapper;

    @Override
    public List<DictVo> queryDictByType(String dictType) {
        List<DictVo> list = new ArrayList<>();
        List<Dict> dicts = ComQueryMapper.queryDictByType(dictType);
        dicts.forEach(dict1 -> {
            list.add(new DictVo(dict1.getDictLabel(), dict1.getDictValue()));
        });
        return list;
    }

    @Override
    public List<DictVo> queryComQueryByCode(String code) {
        ComQuery comQuery = ComQueryMapper.queryComQueryByCode(code);

        return ComQueryMapper.queryComQueryByCustomSql(comQuery.getCustomSql());
    }
}
