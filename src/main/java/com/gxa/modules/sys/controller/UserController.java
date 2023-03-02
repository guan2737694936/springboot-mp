package com.gxa.modules.sys.controller;

import com.gxa.common.utils.ErrorCode;
import com.gxa.common.utils.PageUtils;
import com.gxa.common.utils.Result;
import com.gxa.modules.sys.code.RandomValidateCode;
import com.gxa.modules.sys.entity.User;
import com.gxa.modules.sys.form.UserForm;
import com.gxa.modules.sys.redis.SysUserRedis;
import com.gxa.modules.sys.service.UserService;
import com.gxa.modules.sys.service.UserTokenService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "用户接口")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private SysUserRedis sysUserRedis;


    //生成验证码返回图片,并且保存到redis
    @GetMapping(value = "/sys/getVerify")
    public Result getVerify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        RandomValidateCode randomValidateCode = new RandomValidateCode();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(95, 25, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, 95, 25);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(randomValidateCode.getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= 40; i++) {
            randomValidateCode.drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= 4; i++) {
            randomString = randomValidateCode.drowString(g, randomString, i);
        }
        // 生产验证码字符串并保存到 Redis 中，ip-rightCode，有效期为 1 小时

        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();

        sysUserRedis.addCode(key,randomString);
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
//            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Result<>().ok(key);
    }
    //登录
    @PostMapping("/sys/login")
    public Result login(@RequestBody UserForm userFrom){

        log.info("user------->{}",userFrom);
        String UserInputCaptch = userFrom.getCaptch();
        String uuid = userFrom.getUuid();

        String redisGetCaptch = this.sysUserRedis.getCode(uuid);

        System.out.println("从redis中查询到的验证码---->"+redisGetCaptch);
        System.out.println("用户数输入的验证码---->"+UserInputCaptch);

        System.out.println("对比验证码-->"+redisGetCaptch.equals(UserInputCaptch));

        if (!redisGetCaptch.equals(UserInputCaptch)){
            return new Result().error(ErrorCode.ACCOUNT_PASSWORD_ERROR,"验证码不正确或者已经失效");
        }

        //1、拿着用户名去 查询用户信息
        User user = this.userService.queryByUsername(userFrom.getUsername());
        if(user == null){
            return new Result().error(ErrorCode.ACCOUNT_PASSWORD_ERROR,"用户名或密码不正确");
        }

        //2、通过明文加密  对比  密文 是否一致
        String pwd = new SimpleHash("MD5", userFrom.getPassword(), user.getSalt(), 1024).toString();
        //3、不一致      返回Result.error()
        if(!pwd.equals(user.getPwd())){
            return new Result().error(ErrorCode.ACCOUNT_PASSWORD_ERROR,"用户名或密码不正确");
        }

        //4、一致     生成token 保存redis中 返回Result.ok()
        Result result = this.userTokenService.createToken(user);
        Map map = new HashMap();
        map.put("token",result.getData());
        return new Result().ok(map);
    }

    @RequiresPermissions("sys:user:list")
    @ApiOperation(value="用户分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value ="当前是第几页",dataType ="int"),
            @ApiImplicitParam(paramType = "query",name = "limit",value ="每页显示多少条",dataType ="int"),
            @ApiImplicitParam(paramType = "query",name = "username",value ="查询条件",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "order",value ="升序asc，降序填desc",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "sidx",value ="排序字段",dataType ="String"),

    })
    @GetMapping("/user/list01")
    public Result<PageUtils> list01(@RequestParam @ApiIgnore Map<String,Object> params){
        log.info("----params-----{}----",params);
        PageUtils pageUtils = this.userService.queryByPage01(params);
        return new Result<PageUtils>().ok(pageUtils);
    }

    @GetMapping("/user/list02")
    public Result<PageUtils> list02(@RequestParam Map<String,Object> params){
        log.info("----params-----{}----",params);
        PageUtils pageUtils = this.userService.queryByPage02(params);
        return new Result<PageUtils>().ok(pageUtils);
    }

}
