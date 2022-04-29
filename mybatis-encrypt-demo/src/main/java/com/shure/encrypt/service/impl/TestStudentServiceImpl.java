package com.shure.encrypt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shure.encrypt.dao.TestStudentDao;
import com.shure.encrypt.entity.TestStudent;
import com.shure.encrypt.service.TestStudentService;
import org.springframework.stereotype.Service;

/**
 * (TestStudent)表服务实现类
 *
 * @author makejava
 * @since 2022-04-27 09:38:13
 */
@Service("testStudentService")
public class TestStudentServiceImpl extends ServiceImpl<TestStudentDao, TestStudent> implements TestStudentService {

}

