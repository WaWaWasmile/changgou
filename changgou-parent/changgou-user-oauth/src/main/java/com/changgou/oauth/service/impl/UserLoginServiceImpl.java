package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.UserLoginService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    //实现发送请求
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    /**
     * 响应请求，将请求头，请求体一起传到需要响应的请求中
     * @param clientId
     * @param clientSecret
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthToken login(String clientId, String clientSecret, String username, String password, String grant_type) throws Exception {
        //请求提交的参数封装
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("username",username);
        paramMap.add("password",password);
        paramMap.add("grant_type",grant_type);

        //封装请求头
        String Authorization = "Basic "+new String(Base64.getEncoder().encode((clientId+":"+clientSecret).getBytes()),"UTF-8");
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Authorization",Authorization);

        //HttpEntity->创建该对象 参数1：请求体，参数2：请求头
        //HttpEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers)
        HttpEntity httpEntity = new HttpEntity(paramMap,headerMap);

        //使用客户端负载均衡（loadBalancerClient），从Eureka中获取认证服务器的IP，端口号（uri）
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        URI uri = serviceInstance.getUri();
        String url = uri+"/oauth/token";
        //调用的请求地址 http://localhost:9200/oauth/token
        /**
         * 1.请求方式
         * 2.提交方式
         * 3.requestEntity：提交的数据
         * 4.responseType：返回数据需要提交的类型
         */
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        //System.out.println(response.getBody());
        Map<String,String> map = response.getBody();

        //将令牌信息封装成AuthToken对象
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(map.get("access_token"));
        authToken.setRefreshToken(map.get("refresh_token"));
        authToken.setJti(map.get("jti"));
        return authToken;
    }
}
