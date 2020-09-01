package com.changgou.oauth;

import org.bouncycastle.pqc.crypto.rainbow.RainbowSigner;
import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    @Test
    public void testParesTest(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiYmVpamluZyIsInJvbGUiOiJpdGhlaW1hIiwiYWxpYXMiOiJoZWltYSJ9.Iv7E0viYSjOZyG0_1YIYpSTVsAQ4ClYdLGt355HrsDwiQ1_p4aHcfg9mahT1SPlnjxk4xXzlpUT92AwG3sj-XQFtqdJiPxtThP4szqY48stdlxcwsiVSkq6BN0yITlnvE6b9VspU-RC_tIFB0DgbILF3qep5hqsgDl0NZXlaYKeKPAzNNvL3fKRfyiYyAfihTdkDAQBH5ls9dTNxmT0SE47siyiQeoVSf5tn2c9aqvU1FhVTXO3OFY7w-jtEXSzBVja81ub6bFovCnA_c4hgBoeL_af3FASoSuAJzRDH2Cx8Qd8Cqea4_5wisdqV8IhSKas-D-a2bqCX_4BbVyFcMw";
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyPik5w3IO3LAqGy12nc+Pq6YCarVOBbTcj4+YPyFTJ1a8/SmedqsCYCmhbvk+NY4YXbvMpeCwA6w2EUQqSWeFne4Qf+zovg9EceR2NkJVA+kmwK5V85EKUwoh+fmbhhmYW3pRTnGAzVbYpCGWTeiD1KV22Kh/wXZg/o/e0M84z2RX+ui4JTMQ3ftbjJ7tzsUNdQGpAI0wjhtfgK+Xm68TfmQm3Kdtm5j81nbPc8gVu12ScbWaOQRaHBblUFAGcS4tvZNGAE1a24R5burOZl28LcJw0gg9XM3TkMggy7QQ32R0M97sTUtoJjh4Bo0Wj/KRPclE2SCdi7aOZhepvo3wIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
