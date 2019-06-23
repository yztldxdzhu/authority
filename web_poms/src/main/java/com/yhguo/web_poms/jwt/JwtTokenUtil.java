package com.yhguo.web_poms.jwt;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhguo.web_poms.security.MyUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Assert;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
// application.properties 里面以 jwt 开头的配置
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtil implements Serializable {

    // Signature ： 首先，需要指定一个密钥（secret）。这个密钥只有服务器才知道，不能泄露给用户。
    private String secret;

    // jwt token 有效期
    private Long expiration;

    // jwt token http 的头部
    private String header;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static CompressionCodecResolver codecResolver = new DefaultCompressionCodecResolver();

    /**
     * 从 claims （数据声明）生成 jwt token
     *
     * @param claims 数据声明
     * @return jwt token
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从 userDetails 生成 jwt token
     *
     * @param userDetails 用户详情
     * @return jwt token
     * 
     * JWT 的三个部分依次如下：
     * Header（头部）
     * Payload（负载）
     * Signature（签名）
     * 写成一行，就是：Header.Payload.Signature
     * 
     * （一）Header 部分是一个 JSON 对象，描述 JWT 的元数据，通常是下面的样子：
     * {
     * "alg": "HS256",
     * "typ": "JWT"
     * }
     * alg属性表示签名的算法（algorithm），默认是 HMAC SHA256（写成 HS256）
     * typ属性表示这个令牌（token）的类型（type），JWT 令牌统一写为JWT
     * 最后，将上面的 JSON 对象使用 Base64URL 算法转成字符串。
     * 
     * （二）Payload 部分也是一个 JSON 对象，用来存放实际需要传递的数据。JWT 规定了7个官方字段，供选用：
     * iss (issuer)：签发人
     * exp (expiration time)：过期时间，这个过期时间必须要大于签发时间
     * sub (subject)：主题？所面向的用户
     * aud (audience)：受众，接收jwt的一方
     * nbf (Not Before)：生效时间，定义在什么时间之前，该jwt都是不可用的
     * iat (Issued At)：签发时间
     * jti (JWT ID)：编号，jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     * 除了官方字段，你还可以在这个部分定义私有字段：
     * {
     * "sub": "1234567890",
     * "name": "John Doe",
     * "admin": true
     * }
     * 注意，JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。
     * 这个 JSON 对象也要使用 Base64URL 算法转成字符串。
     * 
     * （三）Signature 部分是对前两部分的签名，防止数据篡改。
     * 首先，需要指定一个密钥（secret）。这个密钥只有服务器才知道，不能泄露给用户。
     * 然后，使用 Header 里面指定的签名算法（默认是 HMAC SHA256），按照下面的公式产生签名：
     * HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
     * 算出签名以后，把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（.）分隔，就可以返回给用户。
     * 
     * Header 和 Payload 串型化的算法是 Base64URL。这个算法跟 Base64 算法基本类似，但有一些小的不同：
     * JWT 作为一个令牌（token），有些场合可能会放到 URL（比如 api.example.com/?token=xxx）。
     * Base64 有三个字符+、/和=，在 URL 里面有特殊含义，所以要被替换掉：=被省略、+替换成-，/替换成_ 。这就是 Base64URL 算法。
     */
    public String generateToken(MyUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("userId", userDetails.getUserId());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从 jwt token 中获取 claims （数据声明）
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    /**
     * 从 jwt token 中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            // 用户名存在 token 的 sub 字段
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从 jwt token 中获取用户id
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserIdFromToken(String token) {
        String userId;
        try {
            Claims claims = getClaimsFromToken(token);
            // 用户id存在 token 的 userId 字段
            userId = (String) claims.get("userId");
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 判断 jwt token 是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            // 有效时间
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新 jwt token，即重新生成一份 jwt token
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String newToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            newToken = generateToken(claims);
        } catch (Exception e) {
            e.printStackTrace();
            newToken = null;
        }
        return newToken;
    }

    /**
     * 验证 jwt token
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        MyUserDetails myUserDetails = (MyUserDetails) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(myUserDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * 解析 jwt token 的 Payload 内容
     *
     * @param jwtHeader http请求头部：jwtHeader
     * @return Map
     */
    public static Map parseJwtPayload(String jwtHeader) {
        Assert.hasText(jwtHeader, "jwtHeader string argument cannot be null or empty.");
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        for (char c : jwtHeader.toCharArray()) {
            if (c == '.') {
                CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
                String token = tokenSeq != null ? tokenSeq.toString() : null;
                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        if (delimiterCount != 2) {
            String msg = "jwtHeader strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("jwtHeader string '" + jwtHeader + "' is missing a body/payload.");
        }
        // =============== Header =================
        Header header;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = readValue(origValue);
            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = codecResolver.resolveCompressionCodec(header);
        }
        // =============== Body =================
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, io.jsonwebtoken.lang.Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        JSONObject jsonObject = JSONObject.parseObject(payload);
        Map<String, Object> map = jsonObject;
        return map;
    }

    /**
     * 从json数据中读取格式化map
     *
     * @param val 值
     * @return Map<String, Object>
     */
    public static Map<String, Object> readValue(String val) {
        try {
            return MAPPER.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + val, e);
        }
    }

}