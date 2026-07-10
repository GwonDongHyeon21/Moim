package com.moim.backend.core.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtProvider(
    @Value($$"${jwt.secret}") private val secretKey: String,
    @Value($$"${jwt.access-expiration}") private val accessExpiration: Long,
    @Value($$"${jwt.refresh-expiration}") private val refreshExpiration: Long,
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun createAccessToken(userId: Long, email: String): String {
        val nowDate = Date()
        val expirationDate = Date(nowDate.time + accessExpiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("email", email)
            .setIssuedAt(nowDate)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun createRefreshToken(userId: Long, sessionId: String): String {
        val nowDate = Date()
        val expirationDate = Date(nowDate.time + refreshExpiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("sessionId", sessionId)
            .setIssuedAt(nowDate)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false // 만료되었거나 위조된 토큰
        } catch (e: IllegalArgumentException) {
            false // 토큰이 비어있는 경우
        }
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject.toLong()
    }

    fun getSessionIdFromToken(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims["sessionId"].toString()
    }
}