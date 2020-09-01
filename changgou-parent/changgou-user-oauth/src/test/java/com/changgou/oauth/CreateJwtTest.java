package com.changgou.oauth;

import com.alibaba.fastjson.JSON;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取证书中的私钥并使用私钥加盐生成令牌
 */
public class CreateJwtTest {

    @Test
   public void testCreateToken(){
        //加载证书
        ClassPathResource resource = new ClassPathResource("changgou.jks");
        //获取证书的数据，加载读取证书数据
        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(resource,"changgou".toCharArray());

        //获取证书中的一对密钥，公钥私钥
        KeyPair keyPair = keyFactory.getKeyPair("changgou", "changgou".toCharArray());

        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //创建令牌，用私钥加盐作为签名
        Map<String,String> map = new HashMap<>();
        map.put("alias","heima");
        map.put("address","beijing");
        map.put("role","itheima");

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(privateKey));
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
