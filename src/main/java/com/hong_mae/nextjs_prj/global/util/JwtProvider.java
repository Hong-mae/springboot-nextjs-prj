package com.hong_mae.nextjs_prj.global.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hong_mae.nextjs_prj.domain.member.entity.Member;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;

    private SecretKey cachedSecretKey;

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null)
            cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());

        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String genRefeshToken(Member member) {
        return genToken(member, 60 * 60 * 24 * 365 * 1);
    }

    public String genAccessToken(Member member) {
        return genToken(member, 60 * 10);
    }

    public String genToken(Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", member.getId());
        claims.put("username", member.getUsername());

        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .claim("payload", Util.json.toStr(claims))
                .expiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SIG.HS512)
                .compact();
    }

    public boolean verify(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Map<String, Object> getClaims(String token) {
        String payload = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("payload", String.class);

        return Util.toMap(payload);
    }
}
