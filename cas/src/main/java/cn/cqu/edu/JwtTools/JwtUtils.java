package cn.cqu.edu.JwtTools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {
    // 常量区 修改时务必注意每个常量的含义和作用
    private static final String SECRET_KEY = "CQU"; // 默认秘钥
    private static final long TIME_LIMIT = 86400000; // 默认有效时长: 一天

    /**
     * 获得一个jwt的串,秘钥和默认时长由类常量给出
     * 
     * @param userid 用户的id
     * @return jwt (String)
     */
    public static String createToken(String userid) {
        Algorithm algorithm = null;
        algorithm = Algorithm.HMAC256(SECRET_KEY);
        long currTime = System.currentTimeMillis();
        Date expireDate = new Date(currTime + TIME_LIMIT);

        String token = JWT.create().withClaim("user_id", userid) // 用户id,验证时凭借这个字段判定登录用户
                .withExpiresAt(expireDate) // 有效时间
                .withIssuedAt(new Date(currTime)) // jwt生成时间
                .sign(algorithm); // 签名
        return token;
    }

    /**
     * 获得jwt中包含的信息,这里为用户的userid
     * 
     * @param token jwt串
     * @return userid,若为null表示jwt有问题或者超时
     */
    public static String getPayload(String token) {
        DecodedJWT jwt = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        jwt = verifier.verify(token);
        Claim userId = jwt.getClaims().get("user_id");
        if (userId != null) {
            return userId.asString();
        } else
            return null;
    }

    public static void main(String[] args) {
        System.out.println("可以显示中文");
        String token = JwtUtils.createToken("HelloWorld");
        System.out.println(token);
        String uid = JwtUtils.getPayload(token);
        if (uid != null) {
            System.out.println(uid);
        } else {
            System.out.println("No");
        }
    }
}
