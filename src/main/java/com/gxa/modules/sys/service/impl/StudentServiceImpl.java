package com.gxa.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxa.common.utils.PageUtils;
import com.gxa.common.utils.Query;
import com.gxa.modules.sys.entity.Student;
import com.gxa.modules.sys.mapper.StudentMapper;
import com.gxa.modules.sys.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {
    @Override
    public PageUtils queryStudent(Map<String, Object> params) {
        String name = (String) params.get("name");
        String age = (String) params.get("age");
        IPage<Student> page = this.page(new Query<Student>().getPage(params),
                new QueryWrapper<Student>().like(StringUtils.isNotEmpty(name),"name",name)
                        .eq(StringUtils.isNotEmpty(age),"age",age));

        return  new PageUtils(page);
    }
}
