package cn.cqu.edu.JwtTools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {
    private static final String SECRET_KEY = "CQU"; // 默认秘钥
    private static final long TIME_LIMIT = 86400000; // 默认有效时长: 一天


    // 利用用户id创建jwt令牌
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

    // 获得jwt中包含的信息,这里为用户的userid
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
}
