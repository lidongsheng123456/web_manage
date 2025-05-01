package com.example.system.service;

import com.example.system.domain.vo.DictVo;

import java.util.List;

public interface ComQueryService {
    List<DictVo> queryDictByType(String dictType);

    List<DictVo> queryComQueryByCode(String code);
}
