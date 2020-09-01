package com.changgou.oauth.controller;

import com.changgou.oauth.service.UserLoginService;
import com.changgou.oauth.util.AuthToken;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserLoginController {

    @Value("{auth.clientId}")
    String clientId;

    @Value("{auth.clientSecret}")
    String clientSecret;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 登录方法
     * 参数传递：
     *        1.账号 username=szitheima
     *        2.密码 password=szitheima
     *        3.授权方式 grant_type=password
     * 请求头传递
     *         Basic Base64(客户端ID：客户端密钥) Authorization
     * @param username
     * @param password
     * @return
     */
    @GetMapping(value = "/login")
    public Result login(String username,String password) throws Exception {
        String grant_type = "password";
        AuthToken login = userLoginService.login(clientId, clientSecret, username, password, grant_type);
        if(login!=null){
            return new Result(true, StatusCode.OK,"登录成功",login);
        }
        return new Result(true, StatusCode.OK,"登录失败");
    }




}
