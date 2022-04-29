package com.shure.encrypt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shure.encrypt.entity.TestStudent;
import com.shure.encrypt.service.TestStudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (TestStudent)表控制层
 *
 * @author makejava
 * @since 2022-04-27 09:38:13
 */
@RestController
@RequestMapping("test")
public class TestStudentController {

    @Autowired
    TestStudentService testStudentService;

    @GetMapping("index")
    public String test(String name) {
        QueryWrapper<TestStudent> wrapper = new QueryWrapper<TestStudent>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", "%" + name + "%");
        }
        return testStudentService.list(wrapper).toString();
    }

    @PostMapping("save")
    public TestStudent save(@RequestBody TestStudent student) {
        if (student != null) {
            testStudentService.save(student);
        }
        return student;
    }
}

