package com.gxa.modules.sys.redis;

import com.gxa.common.utils.JsonUtils;
import com.gxa.common.utils.RedisKeys;
import com.gxa.common.utils.RedisUtils;
import com.gxa.modules.sys.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SysUserRedis {

    private RedisUtils redisUtils;

    public void addToken(String token, User user){

        //token --- user
        //1111--user
        /**
         * sys:user:username:1111  ---user
         * sys:user:username:2222 ----user
         * sys:user:username:3333 ----user
         */
        this.redisUtils.set(RedisKeys.getSysUserTokenKey(user.getUsername(),token),user);
    }
    public User getUserByToken(String token){
        String userJsonStr = this.redisUtils.get(RedisKeys.getSysUserTokenKey(token));
        User user = JsonUtils.parseObject(userJsonStr, User.class);
        return user;
    }

    //   添加redis验证码
    public void addCode(String key, String value){
        this.redisUtils.set(key,value,1000000);
    }
    //   取redis验证码
    public String getCode(String key){
        String code = this.redisUtils.get(key);
        return code;
    }

}
