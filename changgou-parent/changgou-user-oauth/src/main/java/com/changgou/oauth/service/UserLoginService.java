package com.changgou.oauth.service;


import com.changgou.oauth.util.AuthToken;

public interface UserLoginService {
    AuthToken login(String clientId, String clientSecret, String username, String password, String grant_type) throws Exception;
}
