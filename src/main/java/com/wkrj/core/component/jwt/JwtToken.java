package com.wkrj.core.component.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt token
 * @author wxf
 *
 */
public class JwtToken {
	public static void main(String[] args) {
		//String t = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDQ0MzE3MDEsInVzZXJJZCI6ImVjNDcxMWYwYTU0ZTQwMTM5ZjJmMDhjMzMwMjljOWU3IiwiaXNzIjoiU2VydmljZSIsImF1ZCI6IkFQUCIsImlhdCI6MTU0MzU2NzcwMX0.FENsd-6Itwq73JGc6j4XOiFydIr3Cn_GZcDF7lxcZK8";
		String t = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTQ1NTQ5MDI1LCJ1c2VySWQiOiJlOTk5OWFkMC00ODc3LTQ1ZDQtYmMxZS0wMTdhMmVlYWRhODEiLCJpYXQiOjE1NDQ2ODUwMjV9.GRUKsTK8qNl0bYRkAnRkPvYYLp-Rg1XBgYmMDLqNcOI";
		//JwtToken.verifyToken(t);
		Token parseToken = JwtToken.parseToken(t);
		//System.out.println(parseToken);
	}
    /** token秘钥  */
    public static final String SECRET = "wkrj_springboot_oauth";
    
    public static final int CALENDAR_FIELD = Calendar.DATE;
    /** token 过期时间: 10天 */
    public static final int CALENDAR_INTERVAL = 10;

    /**
     * JWT生成Token
     * JWT构成: header, payload, signature
     * @param userId
     * @return
     * @throws Exception
     */
    public static String createToken(String userId) throws Exception {
    	// jwt的签发时间
        Date iatDate = new Date();
        // 当前日历
        Calendar nowTime = Calendar.getInstance();
        // 当前日历 +10天
        nowTime.add(CALENDAR_FIELD, CALENDAR_INTERVAL);
        // 设置过期时间
        Date expiresDate = nowTime.getTime();

        // 头部：header
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256"); // 算法
        map.put("typ", "JWT"); // 类型
        
        // 构建Token52
        String token = JWT.create()
        		// 头部：header
        		.withHeader(map) 
        		// 载荷：payload
                .withClaim("iss", "Service") // jwt签发者
                .withClaim("aud", "APP") // 接收jwt的一方
                .withClaim("userId", null == userId ? null : userId) //用户ID
                .withIssuedAt(iatDate) // jwt的签发时间
                .withExpiresAt(expiresDate) // jwt的过期时间，这个过期时间必须要大于签发时间
                // 签证：signature
                .sign(Algorithm.HMAC256(SECRET)); 

        return token;
    }

    /**
     * 解密Token
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = null;
        DecodedJWT jwt = null;
		try {
			verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			jwt = verifier.verify(token);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Token验证失败");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Token验证失败");
		} catch (JWTDecodeException e) {
			throw new TokenExpiredException("Token解析失败");
		} catch (SignatureVerificationException e) {
			throw new TokenExpiredException("Token签名无效，请重新获取Token");
		} catch (TokenExpiredException e) {
			throw new TokenExpiredException("Token已过期，请重新获取Token");
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Token验证失败", e);
		}catch (Exception e) {
			throw new RuntimeException("Token验证失败", e);
		}
        return jwt.getClaims();
    }

    /**
     * 根据Token获取信息
     * @param tokenStr
     * @return
     */
    public static Token parseToken(String tokenStr) {
        
    	Map<String, Claim> claims = verifyToken(tokenStr);
       
    	Claim userIdClaim = claims.get("userId");
        Claim expClaim = claims.get("exp");
        
        Token  token = new Token();
        
        token.setUserId(userIdClaim==null ? null : userIdClaim.asString());
        token.setExpireTime(expClaim==null ? null : expClaim.asDate());
        
        return token;
    }
}

