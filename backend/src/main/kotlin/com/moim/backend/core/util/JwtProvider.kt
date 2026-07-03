package com.moim.backend.core.util

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

    fun createRefreshToken(userId: Long): String {
        val nowDate = Date()
        val expirationDate = Date(nowDate.time + refreshExpiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(nowDate)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}