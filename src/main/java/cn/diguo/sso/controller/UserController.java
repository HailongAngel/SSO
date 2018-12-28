package cn.diguo.sso.controller;

import cn.diguo.sso.model.User;
import cn.diguo.sso.service.IUserService;
import cn.diguo.sso.util.AesCBC;
import cn.diguo.sso.util.RedisUtil;
import cn.diguo.sso.util.RespUtil;
import cn.diguo.sso.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
/**
 * @program: basi.cproject
 * @description:
 * @author: Hailong
 * @create: 2018-12-11 10:14
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil ru;//注入JedisPool

    String sKey="0CoJUm6Qyw8W8jud";
    String ivParameter="0102030405060708";


    @PostMapping("checkLogin.do")
    public ServiceResponse Login(@RequestBody User user, HttpServletResponse response) throws Exception {
        ServiceResponse responseS = new ServiceResponse();
        //1.判断用户名和密码是否正确
        String username = user.getUsername();
        String password = user.getPassword();
        //判断输入的用户名和密码是否为空
        if (username == null || password == null) {
            responseS.setCode("1111");
            responseS.setMessage("用户名或者密码不能为空");
            return responseS;
        }
        //去查找数据库里面是否存放着传入参数相对应的用户信息
        User info = userService.LoginInfo(username);
        if (info == null) {
            responseS.setCode("1112");
            responseS.setMessage("用户信息不匹配");
            return responseS;
        }
        //有用户信息的话，查看数据库中的密码和登录时候传入的参数是否一致
        if (info.getPassword() .equals(password) ) {
            RespUtil.setSuccessResp("登录成功",responseS,info);
        }
        //2、登录成功后生成token。Token相当于原来的jsessionid。
        String token = UUID.randomUUID().toString();
        //获取ShardedJedis对象
        ru.set(token,info.getUsername()+":"+info.getPassword());
       /* Jedis jedis = jedisPool.getResource();
        //存入键值对
        jedis.set(token,info.getUsername()+":"+info.getPassword());*/
        //设置key过期时间
        //  jedis.expire(token,60);
        //回收ShardedJedis实例
       // jedis.close();
        //设置Cookie,并且使用AES算法进行加密
        String tokenEn = AesCBC.getInstance().encrypt(token,"utf-8",sKey,ivParameter);
        Cookie cookie = new Cookie("ssocookie",tokenEn);
        cookie.setPath("/");
        response.addCookie(cookie);
        return responseS;
    }

   //如果之前登录过并且信息检验成功之后cookie里面就有值了，这时候查看cookie
    @PostMapping("getUserByToken.do")
    public ServiceResponse getUserByToken(HttpServletRequest request) throws Exception {
        ServiceResponse responseS = new ServiceResponse();
        Cookie[] cookies = request.getCookies();
        //获取ShardedJedis对象
       // Jedis jedis = jedisPool.getResource();
        //根据键值获得数据
        if (cookies != null){
            for (Cookie cookie :cookies
                    )
            {
                //将加密的token进行解密
                String tokende = AesCBC.getInstance().decrypt(cookie.getValue(),"utf-8",sKey,ivParameter);
                //去redis里面查找对应的token

                String result = ru.get(tokende).toString();
                if (result!=null){
                    //jedis.close();
                    RespUtil.setSuccessResp("登录成功",responseS,result);
                    break;
                }

            }
        }
        return responseS;

    }


}
