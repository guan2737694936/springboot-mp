package com.gxa.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user")
public class User implements Serializable {

    private Integer id;

    @TableField("user_name")
    private String username;
    private String pwd;
    private String salt;


}
