package com.gxa.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxa.common.utils.PageUtils;
import com.gxa.modules.sys.entity.Student;

import java.util.Map;

public interface StudentService extends IService<Student> {
    PageUtils queryStudent(Map<String,Object> params);
}
