package com.gxa.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_student")
public class Student {
    private int id;
    private String name;
    private int age;
}
