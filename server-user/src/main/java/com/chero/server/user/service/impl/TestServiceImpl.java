package com.chero.server.user.service.impl;

import com.chero.server.user.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by hxh on 2018/6/19.
 */
@Service
public class TestServiceImpl implements TestService{
    @Override
    public void testException() {
        throw new RuntimeException("测试异常抛出");
    }
}
