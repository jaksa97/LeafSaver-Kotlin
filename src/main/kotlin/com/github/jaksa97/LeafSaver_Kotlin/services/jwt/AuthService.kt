package com.github.jaksa97.LeafSaver_Kotlin.services.jwt

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.AuthResponse
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.LoginRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RegisterRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.UserMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val _userRepository: UserRepository,
    private val _userMapper: UserMapper,
    private val _passwordEncoder: PasswordEncoder,
    private val _jwtService: JwtService,
    private val _authManager: AuthenticationManager
) {

    fun register(registerRequest: RegisterRequest): AuthResponse {
        val userToRegister = UserSaveDto(
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            email = registerRequest.email,
            password = _passwordEncoder.encode(registerRequest.password),
            role = registerRequest.role
        )

        val user = _userRepository.save(_userMapper.toEntity(userToRegister))

        val token = _jwtService.generateToken(user)

        return AuthResponse(token)
    }

    @Throws(ResourceNotFoundException::class)
    fun login(loginRequest: LoginRequest): AuthResponse {
        _authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )

        val user = _userRepository.findByEmail(loginRequest.email).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        val token = _jwtService.generateToken(user)

        return AuthResponse(token)
    }
}