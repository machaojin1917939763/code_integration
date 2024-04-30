package cn.machaojin.tool;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 15:54
 */
public class JwtTokenUtil {

    //定义token返回头部
    public static final String AUTH_HEADER_KEY = "Authorization";

    //token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    //签名密钥
    public static final String SECRET_KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x";

    //有效期默认为 2hour
    public static final Long EXPIRATION_TIME = 1000L*60*60*2;


    /**
     * 创建TOKEN
     * @param username
     * @return
     */
    public static String createToken(String username){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 设置Token有效期为1小时
        long expMillis = nowMillis + 3600000;
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                // 使用HS256算法和密钥进行签名
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 验证token
     * @param token
     */
    public static Claims verifyToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (SignatureException e) {
            // 签名异常处理
            throw new RuntimeException("Token验证失败");
        }
    }
}