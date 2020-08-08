import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * 令牌的生成与解析
 */
public class TestGateway {
    /**
     * 生成令牌
     */
    @Test
    public void testCreateToken(){
        JwtBuilder jwtBuilder = Jwts.builder();
        //jwtBuilder.setAudience("itheima");
        jwtBuilder.setIssuedAt(new Date());//令牌颁发时间
        jwtBuilder.setIssuer("aaa");//令牌颁发者
        jwtBuilder.signWith(SignatureAlgorithm.HS256,"itheima");
        System.out.println(jwtBuilder.compact());
    }

    @Test
    public void testParseToken(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTY4MDk3NjUsImlzcyI6ImFhYSJ9.Dx3mwdDYeYZCVgmxGIcCCZ3_cjfGBGzRI1D7unFs3e0";
        Claims claims = Jwts.parser().setSigningKey("itheima").parseClaimsJws(token).getBody();
        System.out.println(claims);
    }
}
