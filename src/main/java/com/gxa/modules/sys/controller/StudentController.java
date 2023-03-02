package com.gxa.modules.sys.controller;

import com.gxa.common.utils.PageUtils;
import com.gxa.common.utils.Result;
import com.gxa.modules.sys.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "学生接口")
@RestController
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequiresPermissions("sys:student:list")
    @ApiOperation(value="学生分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value ="当前是第几页",dataType ="int"),
            @ApiImplicitParam(paramType = "query",name = "limit",value ="每页显示多少条",dataType ="int"),
            @ApiImplicitParam(paramType = "query",name = "name",value ="查询条件",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "age",value ="查询条件",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "order",value ="升序asc，降序填desc",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "sidx",value ="排序字段",dataType ="String"),
    })

    @PostMapping("/student/list")
    public Result<PageUtils> queryStudent(@RequestParam Map<String,Object> params){
        System.out.println("params-------"+params);

        log.info("---------{}----------"+params);
        PageUtils pageUtils = studentService.queryStudent(params);

        return new Result<PageUtils>().ok(pageUtils);
    }
}
