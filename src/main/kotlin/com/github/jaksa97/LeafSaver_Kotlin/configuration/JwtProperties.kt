package com.github.jaksa97.LeafSaver_Kotlin.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProperties(

    @Value("\${application.security.jwt.key}")
    val key: String,

    @Value("\${application.security.jwt.access-token-expiration}")
    val accessTokenExpire: Long,

    @Value("\${application.security.jwt.refresh-token-expiration}")
    val refreshTokenExpire: Long
)