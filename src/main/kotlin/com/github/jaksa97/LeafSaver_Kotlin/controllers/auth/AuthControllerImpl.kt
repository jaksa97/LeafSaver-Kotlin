package com.github.jaksa97.LeafSaver_Kotlin.controllers.auth

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.AuthResponse
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.LoginRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RefreshTokenRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RegisterRequest
import com.github.jaksa97.LeafSaver_Kotlin.services.jwt.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth")
class AuthControllerImpl(
    private val _authService: AuthService
): AuthController {
    override fun register(registerRequest: RegisterRequest): AuthResponse {
        return _authService.register(registerRequest)
    }

    @Throws(ResourceNotFoundException::class)
    override fun login(loginRequest: LoginRequest): AuthResponse {
        return _authService.login(loginRequest)
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse) {

    }

    @Throws(ResourceNotFoundException::class, Exception::class)
    override fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        return _authService.refreshToken(request)
    }

}