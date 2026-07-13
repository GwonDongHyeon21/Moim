package com.moim.backend.core.jwt

import com.moim.backend.core.error.ErrorCode
import com.moim.backend.core.error.ErrorException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return path.startsWith("/api/v1/users/login") ||
                path.startsWith("/api/v1/users/reissue") ||
                path.startsWith("/api/v1/users/logout")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = resolveToken(request)

            accessToken?.let {
                if (!jwtProvider.validateToken(it)) {
                    throw ErrorException(
                        httpStatus = HttpStatus.UNAUTHORIZED,
                        errorCode = ErrorCode.EXPIRED_TOKEN
                    )
                }

                val userId = jwtProvider.getUserIdFromToken(it)
                val authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())

                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        } catch (e: ErrorException) {
            handlerExceptionResolver.resolveException(request, response, null, e)
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}