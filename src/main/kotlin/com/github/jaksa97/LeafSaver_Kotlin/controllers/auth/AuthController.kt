package com.github.jaksa97.LeafSaver_Kotlin.controllers.auth

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.AuthResponse
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.LoginRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RefreshTokenRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface AuthController {

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register new user")
    fun register(@RequestBody registerRequest: RegisterRequest): AuthResponse


    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login user")
    @Throws(ResourceNotFoundException::class)
    fun login(@RequestBody loginRequest: LoginRequest): AuthResponse

    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Logout user")
    fun logout(request: HttpServletRequest, response: HttpServletResponse)

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh token", description = "Refresh existing access token")
    @Throws(ResourceNotFoundException::class, Exception::class)
    fun refreshToken(@RequestBody request: RefreshTokenRequest): AuthResponse
}