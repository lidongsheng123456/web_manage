package com.example.service;

import com.example.system.domain.ComQuery;
import com.example.system.domain.Dict;
import com.example.system.domain.vo.DictVo;
import com.example.system.mapper.ComQueryMapper;
import com.example.system.service.impl.ComQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ComQueryServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ComQueryServiceImpl 通用查询服务测试")
class ComQueryServiceImplTest {

    @Mock
    private ComQueryMapper comQueryMapper;

    private ComQueryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ComQueryServiceImpl(comQueryMapper);
    }

    @Test
    @DisplayName("queryDictByType 返回字典列表")
    void queryDictByTypeSuccess() {
        Dict dict = new Dict();
        dict.setDictLabel("男");
        dict.setDictValue(Integer.valueOf("1"));
        dict.setTagType("success");

        when(comQueryMapper.queryDictByType("gender")).thenReturn(List.of(dict));

        List<DictVo> result = service.queryDictByType("gender");

        assertEquals(1, result.size());
        assertEquals("男", result.get(0).getDictLabel());
        assertEquals(1, result.get(0).getDictValue());
        assertEquals("success", result.get(0).getTagType());
    }

    @Test
    @DisplayName("queryDictByType 空列表")
    void queryDictByTypeEmpty() {
        when(comQueryMapper.queryDictByType("unknown")).thenReturn(List.of());

        List<DictVo> result = service.queryDictByType("unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("queryComQueryByCode 查询动态字典")
    void queryComQueryByCodeSuccess() {
        ComQuery comQuery = new ComQuery();
        comQuery.setCustomSql("SELECT id, name FROM users");

        when(comQueryMapper.queryComQueryByCode("user_status")).thenReturn(comQuery);

        DictVo dictVo = new DictVo("标签", 1, "info");
        when(comQueryMapper.queryComQueryByCustomSql("SELECT id, name FROM users"))
                .thenReturn(List.of(dictVo));

        List<DictVo> result = service.queryComQueryByCode("user_status");

        assertEquals(1, result.size());
        assertEquals("标签", result.get(0).getDictLabel());
        verify(comQueryMapper).queryComQueryByCode("user_status");
        verify(comQueryMapper).queryComQueryByCustomSql("SELECT id, name FROM users");
    }
}
