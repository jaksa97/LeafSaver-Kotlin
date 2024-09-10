package com.github.jaksa97.LeafSaver_Kotlin.models.auth

import io.swagger.v3.oas.annotations.media.Schema

data class LoginRequest(
    @Schema(example = "jaksa97@gmail.com")
    val email: String,
    @Schema(example = "password")
    val password: String
)