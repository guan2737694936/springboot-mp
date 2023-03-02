package com.gxa.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxa.common.utils.PageUtils;
import com.gxa.modules.sys.entity.Student;

import java.util.Map;

public interface StudentMapper extends BaseMapper<Student> {
    PageUtils queryStudent(Map<String,Object> params);
}
