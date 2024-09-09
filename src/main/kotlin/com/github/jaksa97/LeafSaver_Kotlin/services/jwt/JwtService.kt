package com.github.jaksa97.LeafSaver_Kotlin.services.jwt

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import com.github.jaksa97.LeafSaver_Kotlin.repositories.TokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtService(
    private val _tokenRepository: TokenRepository
) {

    private val secret = Keys.hmacShaKeyFor(System.getenv("JWT_KEY").toByteArray())

    fun generateToken(user: UserEntity): String =
        Jwts.builder()
            .subject(user.email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis()+ 24*60*60*1000))
            .signWith(secret)
            .compact()

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(secret)
            .build()
            .parseSignedClaims(token)
            .payload

    fun <T> extractClaim(token: String, resolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)

        return resolver.apply(claims)
    }

    fun extractEmail(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun isValid(token: String, user: UserDetails): Boolean {
        val email = extractEmail(token)

        val isValidToken = _tokenRepository.findByToken(token).map { !it.loggedOut }.orElse(false)

        return email == user.username && !isTokenExpired(token) && isValidToken
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date(System.currentTimeMillis()))
    }

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)
}