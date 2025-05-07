package com.example.system.service.impl;

import com.example.system.mapper.UserOperationMapper;
import com.example.system.service.UserOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOperationServiceImpl implements UserOperationService {
    private final UserOperationMapper userOperationMapper;
}
