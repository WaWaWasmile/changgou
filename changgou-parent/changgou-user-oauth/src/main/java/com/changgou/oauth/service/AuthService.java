package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;
import org.springframework.stereotype.Service;

//@Service
public interface AuthService {
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
